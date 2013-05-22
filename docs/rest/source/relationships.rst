.. _relationships:

/api/v1/lang/relationships
==========================

Relationships of the BEL language.

.. ############################################################################
.. http:get:: /api/v1/lang/relationships

    | **Description**
    | Get a summary of all language relationships.
    | (try it: http://demo.openbel.org/api/v1/lang/relationships)

    **Links**

    | *self*
    |   All relationships (this resource)
    | *start*
    |   The first relationship
    | *end*
    |  The last relationship

    | **Status codes**:
    |   :http:statuscode:`200`


.. ############################################################################
.. http:get:: /api/v1/lang/relationships/{relationship}

    | **Description**
    | Get one relationship resource.
    | (try it: http://demo.openbel.org/api/v1/lang/relationships/increases)

    Relationships can also be retrieved via their abbreviation. For example,
    ``->`` is the abbreviation for the ``increases`` relationship so the path
    ``/api/v1/lang/relationships/->`` works just as well. Relationships are
    comprised of ``description``, ``name``, optional ``abbreviation``, and
    ``metadata``.

    **Links**

    Relationship resources provide a number of useful links.

    | *self*
    |   Itself
    | *index*
    |   All relationships
    | *first*
    |   The first relationship
    | *next*
    |   The next relationship
    | *prev*
    |   The previous relationship
    | *last*
    |   The last relationship

    | **Status codes**:
    |   :http:statuscode:`200`
