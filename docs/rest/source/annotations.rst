.. _annotations:

/api/v1/annotations
===================

Version 1 API annotations.

.. ############################################################################
.. http:get:: /api/v1/annotations

    | **Description**
    | Get a summary of all annotation resources.
    | (try it: http://demo.openbel.org/api/v1/annotations)

    **Links**

    This resource includes links out to the ``start`` and ``end`` of the
    annotation resources.

    | *self*
    |   All annotations (this resource)
    | *start*
    |   The first annotation
    | *end*
    |  The last annotation

    | **Status codes**:
    |   :http:statuscode:`200`


.. ############################################################################
.. http:get:: /api/v1/annotations/{keyword}

    | **Description**
    | Get one annotation resource.
    | (try it: http://demo.openbel.org/api/v1/annotations/BodyRegion)

    Annotations are comprised of a ``description``, ``name``, ``usage``,
    ``type``, and ``keyword``. The ``keyword`` is unique to all annotations.

    **Links**

    Annotation resources provide a number of useful links.

    | *self*
    |   Itself
    | *related*
    |   The annotation's values
    | *index*
    |   All annotations
    | *first*
    |   The first annotation
    | *next*
    |   The next annotation
    | *prev*
    |   The previous annotation
    | *last*
    |   The last annotation

    | **Status codes**:
    |   :http:statuscode:`200`
