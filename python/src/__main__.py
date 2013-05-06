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


def out(msg):
    sys.stdout.write(msg)
    sys.stdout.flush()


def check():
    '''Checks MongoDB is setup properly for the BEL REST API.

    Returns True if setup properly, False if fixes are required.
    '''
    print('Starting checks.')
    status = True
    print('Finished checks.')
    print()
    return status


def fix():
    '''
    Applies necessary changes to ensure MongoDB is setup properly for the BEL
    REST API.
    '''
    print('Starting to apply changes.')
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
