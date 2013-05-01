.. _versions:

/api/versions
=============

Provides API version information.

GET
+++

.. http:get:: /api/versions

    API version information.

    **Status codes**:

    - :http:statuscode:`200`

    **Example response**:

    .. sourcecode:: http

        HTTP/1.1 200 OK
        Content-Type: application/json

    .. sourcecode:: javascript

        {
          "versions": [

            {
              // API version 1
              "version": 1,
              "links": [
                {
                  // The root of this API version is at /api/v1
                  "rel": "root",
                  "href": "/api/v1"
                }
              ]
            }

          ]
        }
