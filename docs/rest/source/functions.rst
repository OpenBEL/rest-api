.. _functions:

/api/v1/lang/functions
======================

Functions of the BEL language.

.. ############################################################################
.. http:get:: /api/v1/lang/functions

    | **Description**
    | Get a summary of all language functions.
    | (try it: http://demo.openbel.org/api/v1/lang/functions)

    **Links**

    | *self*
    |   All functions (this resource)
    | *start*
    |   The first function
    | *end*
    |  The last function

    | **Status codes**:
    |   :http:statuscode:`200`


.. ############################################################################
.. http:get:: /api/v1/lang/functions/{function}

    | **Description**
    | Get one function resource.
    | (try it: http://demo.openbel.org/api/v1/lang/functions/abundance)

    Functions can also be retrieved via their abbreviation. For example, ``a``
    is the abbreviation for the ``abundance`` function so the path
    ``/api/v1/lang/functions/a`` works just as well. Functions are comprised of
    ``description``, ``name``, and optional ``abbreviation``.

    **Links**

    Relationship resources provide a number of useful links.

    | *self*
    |   Itself
    | *related*
    |   The function's signatures
    | *index*
    |   All functions
    | *first*
    |   The first function
    | *next*
    |   The next function
    | *prev*
    |   The previous function
    | *last*
    |   The last function

    | **Status codes**:
    |   :http:statuscode:`200`


.. ############################################################################
.. http:get:: /api/v1/lang/functions/signatures/{function}

    | **Description**
    | Get a function's signatures.
    | (try it: http://demo.openbel.org/api/v1/lang/functions/signatures/abundance)

    Signatures can also be retrieved via their abbreviation. For example, ``a``
    is the abbreviation for the ``abundance`` function so the path
    ``/api/v1/lang/functions/signatures/a`` works just as well. Signatures are
    comprised of a ``value``, ``number_of_arguments``, and ``return_type``.

    Every function will have one or more signatures that define how the function
    may be used::

        FUNCTION (ARG1, ARG2, ... ARGN) RETURN_TYPE

    For example, the signature::

        proteinAbundance(E:proteinAundance)proteinAbundance)

    belongs to the ``proteinAbundance`` function. It accepts one namespace
    value suited for *proteinAbundance* arguments and returns a
    ``proteinAbundance``. This signature is leveraged to form complex
    expressions like::

        complexAbundance(proteinAbundance(HGNC:TIMP3),proteinAbundance(HGNC:KDR))

    See the `Signature`_ class for more reference.

    **Links**

    Relationship resources provide a number of useful links.

    | *self*
    |   Itself
    | *related*
    |   The function's signatures
    | *index*
    |   All functions
    | *first*
    |   The first function
    | *next*
    |   The next function
    | *prev*
    |   The previous function
    | *last*
    |   The last function

    | **Status codes**:
    |   :http:statuscode:`200`

.. _Signature: http://openbel.github.io/openbel-framework/org/openbel/framework/common/lang/Signature.html