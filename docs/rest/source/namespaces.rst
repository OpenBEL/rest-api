.. _namespaces:

/api/v1/namespaces
==================

Version 1 API namespaces.

.. ############################################################################
.. http:get:: /api/v1/namespaces

    | **Description**
    | Get a summary of all namespace resources.
    | (try it: http://demo.openbel.org/api/v1/namespaces)

    **Links**

    This resource includes links out to the ``start`` and ``end`` of the
    namespace resources.

    | *self*
    |   All namespaces (this resource)
    | *start*
    |   The first namespace
    | *end*
    |   The last namespace

    | **Status codes**:
    |   :http:statuscode:`200`


.. ############################################################################
.. http:get:: /api/v1/namespaces/{keyword}

    | **Description**
    | Get one namespace resource.
    | (try it: http://demo.openbel.org/api/v1/namespaces/CHEBI)

    **Links**

    Namespace resources provide a number of useful links.

    | *self*
    |   Itself
    | *related*
    |   The namespace's values
    | *index*
    |   All namespaces
    | *first*
    |   The first namespace
    | *next*
    |   The next namespace
    | *prev*
    |   The previous namespace
    | *last*
    |   The last namespace

    | **Status codes**:
    |   :http:statuscode:`200`
