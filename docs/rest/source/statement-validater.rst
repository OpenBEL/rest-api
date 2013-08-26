.. _statement-validater:

/api/v1/lang/validater/statement
================================

BEL statement syntax validation.

.. ############################################################################
.. http:post:: /api/v1/lang/validater/statement

    | **Description**
    | Validates the syntax of a BEL statement.

    Example request:

    .. sourcecode:: http

        POST /api/v1/lang/validater/statement HTTP/1.1
        Host: demo.openbel.org
        Accept: */*
        Content-Type: text/plain; charset=utf-8

    .. code::

          p(HGNC:TIMP3) increases p(HGNC:KDR)

    | **Status codes**:
    |   :http:statuscode:`200`
    |   :http:statuscode:`415` Client did not send plain text
