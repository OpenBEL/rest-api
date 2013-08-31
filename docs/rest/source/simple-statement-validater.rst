.. _simple-statement-validater:

/api/v1/lang/validater/statement/simple
=======================================

Simple BEL statement syntax validation.

.. ############################################################################
.. http:post:: /api/v1/lang/validater/statement/simple

    | **Description**
    | Validates the syntax of a simple BEL statement (i.e., missing a
    | relationship).

    Example request:

    .. sourcecode:: http

        POST /api/v1/lang/validater/statement/simple HTTP/1.1
        Host: demo.openbel.org
        Accept: */*
        Content-Type: text/plain; charset=utf-8

    .. code::

          p(HGNC:TIMP3)

    | **Status codes**:
    |   :http:statuscode:`200`
    |   :http:statuscode:`415` Client did not send plain text
