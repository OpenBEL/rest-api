.. _namespace-keyword-completion:

/api/v1/completion/namespace-keyword/{keyword}
==============================================

Namespace keyword completion.

.. ############################################################################
.. http:get:: /api/v1/completion/namespace-keyword/{keyword}

    | **Description**
    | Complete a namespace keyword.
    | (try it: http://demo.openbel.org/api/v1/completion/namespace-keyword/MESH)

    **Links**

    | *self*
    |   This resource
    | *result*
    |   Completion result
    | *describedby*
    |   API documentation

    Example request:

    In this example, namespaces whose keywords begin with *MESH* are matched
    and returned in the response's values. E.g., ``response["values"]``.

    .. sourcecode:: http

        GET /api/v1/completion/namespace-keyword/MESH HTTP/1.1
        Host: demo.openbel.org
        Accept: */*
        Content-Type: text/plain; charset=utf-8

    Example response:

    .. sourcecode:: http

        HTTP/1.1 202 Accepted
        Content-Type: application/json; charset=UTF-8
        Connection: keep-alive
        Transfer-Encoding: chunked
        Accept-Ranges: bytes
        Date: Thu, 08 Aug 2013 17:58:19 GMT
        Vary: Accept-Charset, Accept-Encoding, Accept-Language, Accept

    .. code::

        {
          "_links": [
            {
              "href": "http://localhost:60000/docs/api/namespace-keyword-completion.html",
              "rel": "describedby"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/namespace-keyword/MESHPP",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/namespace-keyword/MESHD",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/namespace-keyword/MESHCL",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/namespace-keyword/MESH",
              "rel": "self"
            }
          ],
          "values": [
            "MESHPP",
            "MESHD",
            "MESHCL"
          ]
        }
