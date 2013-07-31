.. _translater:

/api/v1/lang/translater
=======================

BEL script, statement, and term syntax validation.

.. ############################################################################
.. http:post:: /api/v1/lang/validater

    | **Description**
    | Validates the syntax of a BEL statement, term, or script.

    Example request:

    .. sourcecode:: http

        POST /api/v1/lang/validater HTTP/1.1
        Host: demo.openbel.org
        Accept: */*
        Content-Type: application/json; charset=utf-8

    .. sourcecode:: javascript

        {
          "term": "complex(p(HGNC:TIMP3),p(HGNC:KDR))",
          "statement": "p(HGNC:TIMP3) increases p(HGNC:KDR)",
          "script": "<BEL script omitted for brevity...>"
        }

    | **Validation**
    | Three types of input are capable of being validated.
    | *term*
    |   Term validation, e.g., ``complex(p(HGNC:TIMP3),p(HGNC:KDR))``
    | *statement*
    |   Statement validation, e.g., ``p(HGNC:TIMP3) -> p(HGNC:KDR)``
    | *script*
    |   BEL script (e.g., `full_abstract1.bel`_)

    Response:

    .. sourcecode:: javascript

        {
          "statement": {
            "valid": true
          },
          "term": {
            "valid": true
          },
          "script": {
            "valid": false,
            "syntax_errors": [
              "Error at line 1, character 10: Namespace has not been defined."
            ],
            "syntax_warnings": []
          }
        }

    | **Status codes**:
    |   :http:statuscode:`200`
    |   :http:statuscode:`400` Invalid JSON body
    |   :http:statuscode:`415` Client did not send JSON

.. _full_abstract1.bel: https://raw.github.com/OpenBEL/openbel-framework-resources/master/knowledge/full_abstract1.bel


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
