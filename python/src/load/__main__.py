#!/usr/bin/env python3
# coding: utf-8
'''
This script pushes namespace value synonyms into MongoDB for the BEL REST API.
'''
import os
import re
import sys
import bson
import pymongo
conn = None
restapi_db = None

# maps namespace prefix to the doc _id representing it
prefix_map = None


def out(msg):
    sys.stdout.write(msg)
    sys.stdout.flush()


def has_target(prefix, target):
    '''Get whether the target of a synonym is in MongoDB.'''
    query = {'nsmeta-id': prefix_map[prefix], 'norm': target.lower()}
    if restapi_db.nsvalues.find_one(query):
        return True
    return False


def set_target_synonyms(prefix, target, synonyms):
    '''Set the synonyms for a target.'''
    query = {'nsmeta-id': prefix_map[prefix], 'val': target}
    update = {'$set': {'synonyms': synonyms}}
    restapi_db.nsvalues.update(query, update)


def main():
    out('Establishing connection to MongoDB... ')
    host = os.getenv('_ENV_MONGO_HOST')
    if host is None:
        print('no _ENV_MONGO_HOST is set')
        sys.exit(1)
    db = os.getenv('_ENV_MONGO_DB')
    if db is None:
        print('no _ENV_MONGO_DB is set')
        sys.exit(1)
    topdir = os.getenv('_ENV_TOPDIR')
    if topdir is None:
        print('no _ENV_TOPDIR is set')
        sys.exit(1)
    global conn
    conn = pymongo.MongoClient(host)
    global restapi_db
    restapi_db = conn[db]
    print('done')
    print()

    out('Mapping namespace prefixes... ')
    global prefix_map
    prefix_map = {x['keyword']: x['_id'] for x in restapi_db.namespaces.find()}
    print('done')
    print()

    with open(os.path.join(topdir, 'Synonyms.tsv')) as synonym_data:
        for total_lines, line in enumerate(synonym_data, 1):
            pass
        synonym_data.seek(0)
        i = 0
        print('Processing synonyms.')
        for synonym in synonym_data:
            tokens = synonym.strip().split('\t')
            prefix, target, syns = tokens[0], tokens[1], tokens[2:]
            if has_target(prefix, target):
                set_target_synonyms(prefix, target, syns)
            i += 1
            if i % 10000 == 0:
                print('Processed %d of %d synonym entries.' % (i, total_lines))
        print('Finished processing synonyms.')

    out('Closing connection to MongoDB... ')
    sys.stdout.flush()
    conn.close()
    print('done')


if __name__ == '__main__':
    main()
    sys.exit(0)
