.. _annotation-keyword-completion:

/api/v1/completion/annotation/{keyword}
=======================================

Annotation keyword completion.

.. ############################################################################
.. http:get:: /api/v1/completion/annotation/{keyword}

    | **Description**
    | Complete an annotation keyword.
    | (try it: http://demo.openbel.org/api/v1/completion/annotation/S)

    **Links**

    | *self*
    |   This resource
    | *result*
    |   Completion result
    | *describedby*
    |   API documentation

    Example request:

    .. sourcecode:: http

        GET /api/v1/completion/annotation/S HTTP/1.1
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
        Date: Thu, 08 Aug 2013 17:58:19 GMT
        Vary: Accept-Charset, Accept-Encoding, Accept-Language, Accept

    .. code::

        {
          "_links": [
            {
              "href": "http://localhost:60000/docs/api/annotation-keyword-completion.html",
              "rel": "describedby"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/annotation/SenseOrgan",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/annotation/StomatognathicSystem",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/annotation/Species",
              "rel": "result"
            },
            {
              "href": "http://localhost:60000/api/v1/completion/annotation/S",
              "rel": "self"
            }
          ],
          "values": [
            "SenseOrgan",
            "StomatognathicSystem",
            "Species"
          ]
        }
