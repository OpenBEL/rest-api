.. _term-validater:

/api/v1/lang/validater/term
===========================

BEL term syntax validation.

.. ############################################################################
.. http:post:: /api/v1/lang/validater/term

    | **Description**
    | Validates the syntax of a BEL term.

    Example request:

    .. sourcecode:: http

        POST /api/v1/lang/validater/term HTTP/1.1
        Host: demo.openbel.org
        Accept: */*
        Content-Type: text/plain; charset=utf-8

    .. code::

          complex(p(HGNC:TIMP3),p(HGNC:KDR))

    | **Status codes**:
    |   :http:statuscode:`200`
    |   :http:statuscode:`415` Client did not send plain text
