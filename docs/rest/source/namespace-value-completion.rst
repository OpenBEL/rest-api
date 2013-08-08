.. _namespace-value-completion:

/api/v1/completion/namespace/{keyword}/{value}
==============================================

Namespace value completion.

.. ############################################################################
.. http:get:: /api/v1/completion/namespace/{keyword}/{value}

    | **Description**
    | Complete a namespace value given its prefix.
    | (try it: http://demo.openbel.org/api/v1/completion/namespace/MESHPP/Auto)

    **Links**

    | *self*
    |   This resource
    | *result*
    |   Completion result
    | *describedby*
    |   API documentation

    Example request:

    In this example, namespace values belonging to the ``MESHPP`` namespace
    starting with *Auto* are matched and returned in the response's values.
    E.g., ``response["values"]``.

    .. sourcecode:: http

        GET /api/v1/completion/namespace/MESHPP/Auto HTTP/1.1
        Host: demo.openbel.org
        Accept: */*
        Content-Type: text/plain; charset=utf-8

    Example response:

    .. sourcecode:: http

        HTTP/1.1 300 Multiple Choices
        Content-Type: application/json; charset=UTF-8
        Connection: keep-alive
        Transfer-Encoding: chunked
        Accept-Ranges: bytes
        Date: Thu, 08 Aug 2013 18:00:54 GMT
        Vary: Accept-Charset, Accept-Encoding, Accept-Language, Accept

    .. code::

        {
          "_links": [
            {
              "href": "http://localhost:60000/docs/api/namespace-value-completion.html",
              "rel": "describedby"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/namespace/MESHPP/Autocrine Communication",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/namespace/MESHPP/Autoimmunity",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/namespace/MESHPP/Autophagy",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/namespace/MESHPP/Autotrophic Processes",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/namespace/MESHPP/Auto",
              "rel": "self"
            }
          ],
          "values": [
            "Autocrine Communication",
            "Autoimmunity",
            "Autophagy",
            "Autotrophic Processes"
          ]
        }
