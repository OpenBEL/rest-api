.. _translater:

/api/v1/lang/translater
=======================

BEL script statement and term translation.

.. ############################################################################
.. http:post:: /api/v1/lang/validater

    | **Description**
    | Produces a ``text/plain`` translation of a BEL statement or term.

    Example request:

    .. sourcecode:: http

        POST /api/v1/lang/translater HTTP/1.1
        Host: demo.openbel.org
        Accept: */*
        Content-Type: text/plain; charset=utf-8

    .. code::

        p(HGNC:MAPK1)

    Example response:

    .. sourcecode:: http

        HTTP/1.1 200 OK
        Content-Type: text/plain; charset=UTF-8
        Connection: keep-alive
        Content-Length: 37
        Accept-Ranges: bytes
        Vary: Accept-Charset, Accept-Encoding, Accept-Language, Accept

    .. code::

        the abundance of HGNC namespace MAPK1
