.. _script-validater:

/api/v1/lang/validater/script
=============================

BEL script syntax validation.

.. ############################################################################
.. http:post:: /api/v1/lang/validater/script

    | **Description**
    | Validates the syntax of a BEL script.

    Example request:

    .. sourcecode:: http

        POST /api/v1/lang/validater/script HTTP/1.1
        Host: demo.openbel.org
        Accept: */*
        Content-Type: text/plain; charset=utf-8

    .. code::

        ##################################################################
        # Document Properties Section
        SET DOCUMENT Name = "BEL Framework Example 1 Document"
        SET DOCUMENT Description = "Example of modeling a full abstract...
        SET DOCUMENT Version = "1.2"
        ...

    | **Status codes**:
    |   :http:statuscode:`200`
    |   :http:statuscode:`415` Client did not send plain text
