.. _annotation-value-completion:

/api/v1/completion/annotation/{keyword}/{value}
==============================================

Annotation value completion.

.. ############################################################################
.. http:get:: /api/v1/completion/annotation/{keyword}/{value}

    | **Description**
    | Complete an annotation value given its prefix.
    | (try it: http://demo.openbel.org/api/v1/completion/annotation/Species/1)

    **Links**

    | *self*
    |   This resource
    | *result*
    |   Completion result
    | *describedby*
    |   API documentation

    Example request:

    .. sourcecode:: http

        GET /api/v1/completion/annotation/Species/1 HTTP/1.1
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
              "href": "http://localhost:60000/docs/api/annotation-value-completion.html",
              "rel": "describedby"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/annotation/Species/10090",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/annotation/Species/10116",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/annotation/Species/1",
              "rel": "self"
            }
          ],
          "values": [
            "10090",
            "10116"
          ]
        }
