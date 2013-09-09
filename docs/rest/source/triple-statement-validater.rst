.. _triple-statement-validater:

/api/v1/lang/validater/statement/triple
=======================================

Triple BEL statement syntax validation.

.. ############################################################################
.. http:post:: /api/v1/lang/validater/statement/triple

    | **Description**
    | Validates the syntax of a BEL statement triple.

    Example request:

    .. sourcecode:: http

        POST /api/v1/lang/validater/statement/triple HTTP/1.1
        Host: demo.openbel.org
        Accept: */*
        Content-Type: text/plain; charset=utf-8

    .. code::

          p(HGNC:TIMP3) increases p(HGNC:KDR)

    | **Status codes**:
    |   :http:statuscode:`200`
    |   :http:statuscode:`415` Client did not send plain text
