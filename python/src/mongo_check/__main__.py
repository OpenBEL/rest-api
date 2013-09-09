#!/usr/bin/env python3
# coding: utf-8
'''
This script checks MongoDB is setup properly for the BEL REST API.
'''
import os
import re
import sys
import bson
import pymongo
conn = None
restapi_db = None
nsvalues_norm_index = True
nsvalues_nsmeta_index = True
nsvalues_val_index = True
nsvalues_synonyms_index = True
annovalues_norm_index = True
namespaces_keyword_index = True
annotations_keyword_index = True


def out(msg):
    sys.stdout.write(msg)
    sys.stdout.flush()


def check():
    '''Checks MongoDB is setup properly for the BEL REST API.

    Returns True if setup properly, False if fixes are required.
    '''
    print('Starting checks.')
    status = True

    # INDEX PRESENT ON NSVALUES NORM FIELD
    coll = restapi_db.nsvalues
    out('Checking for an index on norm in nsvalues... ')
    if 'norm' in coll.index_information():
        print('yes')
    else:
        print('no')
        global nsvalues_norm_index
        nsvalues_norm_index = False
        status = False

    # INDEX PRESENT ON VALUES NSMETA-ID FIELD
    coll = restapi_db.nsvalues
    out('Checking for an index on nsmeta-id in nsvalues... ')
    if 'nsmeta-id' in coll.index_information():
        print('yes')
    else:
        print('no')
        global nsvalues_nsmeta_index
        nsvalues_nsmeta_index = False
        status = False

    # INDEX PRESENT ON VALUES VAL FIELD
    coll = restapi_db.nsvalues
    out('Checking for an index on val in nsvalues... ')
    if 'val' in coll.index_information():
        print('yes')
    else:
        print('no')
        global nsvalues_val_index
        nsvalues_val_index = False
        status = False

    # INDEX PRESENT ON NSVALUES SYNONYMS FIELD
    coll = restapi_db.nsvalues
    out('Checking for an index on synonyms in nsvalues... ')
    if 'synonyms' in coll.index_information():
        print('yes')
    else:
        print('no')
        global nsvalues_synonyms_index
        nsvalues_synonyms_index = False
        status = False

    # INDEX PRESENT ON NAMESPACES KEYWORD
    coll = restapi_db.namespaces
    out('Checking for an index on keyword in namespaces... ')
    if 'keyword' in coll.index_information():
        print('yes')
    else:
        print('no')
        global namespaces_keyword_index
        namespaces_keyword_index = False
        status = False

    # INDEX PRESENT ON ANNOVALUES NORM FIELD
    coll = restapi_db.nsvalues
    out('Checking for an index on norm in annovalues... ')
    if 'norm' in coll.index_information():
        print('yes')
    else:
        print('no')
        global annovalues_norm_index
        annovalues_norm_index = False
        status = False

    # INDEX PRESENT ON ANNOTATIONS KEYWORD
    coll = restapi_db.annotations
    out('Checking for an index on keyword in annotations... ')
    if 'keyword' in coll.index_information():
        print('yes')
    else:
        print('no')
        global annotations_keyword_index
        annotations_keyword_index = False
        status = False

    print('Finished checks.')
    print()
    return status


def fix():
    '''
    Applies necessary changes to ensure MongoDB is setup properly for the BEL
    REST API.
    '''
    print('Starting to apply changes.')
    unique_fg_index_args = {'unique': True, 'background': False}
    fg_index_args = {'background': False}

    if not nsvalues_norm_index:
        out('Creating index on norm in nsvalues... ')
        coll = restapi_db.nsvalues
        kwargs = {'name': 'norm'}
        kwargs.update(fg_index_args)
        coll.create_index('norm', **kwargs)
        print('done')

    if not nsvalues_nsmeta_index:
        out('Creating index on nsmeta-id in nsvalues... ')
        coll = restapi_db.nsvalues
        kwargs = {'name': 'nsmeta-id'}
        kwargs.update(fg_index_args)
        coll.create_index('nsmeta-id', **kwargs)
        print('done')

    if not nsvalues_val_index:
        out('Creating index on val in nsvalues... ')
        coll = restapi_db.nsvalues
        kwargs = {'name': 'val'}
        kwargs.update(fg_index_args)
        coll.create_index('val', **kwargs)
        print('done')

    if not nsvalues_synonyms_index:
        out('Creating index on synonyms in nsvalues... ')
        coll = restapi_db.nsvalues
        kwargs = {'name': 'synonyms'}
        kwargs.update(fg_index_args)
        coll.create_index('synonyms', **kwargs)
        print('done')

    if not namespaces_keyword_index:
        out('Creating index on keyword in namespaces... ')
        coll = restapi_db.namespaces
        kwargs = {'name': 'keyword'}
        kwargs.update(fg_index_args)
        coll.create_index('keyword', **kwargs)
        print('done')

    if not annovalues_norm_index:
        out('Creating index on norm in annovalues... ')
        coll = restapi_db.annovalues
        kwargs = {'name': 'norm'}
        kwargs.update(fg_index_args)
        coll.create_index('norm', **kwargs)
        print('done')

    if not annotations_keyword_index:
        out('Creating index on keyword in annotations... ')
        coll = restapi_db.annotations
        kwargs = {'name': 'keyword'}
        kwargs.update(fg_index_args)
        coll.create_index('keyword', **kwargs)
        print('done')

    print('Finished applying changes.')
    print()


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
    global conn
    conn = pymongo.MongoClient(host)
    global restapi_db
    restapi_db = conn[db]
    print('done')
    print()

    if not check():
        if not '--fix' in sys.argv:
            print('MongoDB is not setup properly, use --fix to correct this.')
            sys.exit(1)
        fix()

    out('Closing connection to MongoDB... ')
    sys.stdout.flush()
    conn.close()
    print('done')


if __name__ == '__main__':
    main()
    sys.exit(0)
