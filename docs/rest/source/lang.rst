.. _lang:

/api/v1/lang
============

BEL language information.

.. ############################################################################
.. http:get:: /api/v1/lang

    | **Description**
    | Links to resources pertaining to the language.
    | (try it: http://demo.openbel.org/api/v1/lang)

    **Links**

    | *self*
    |   This resource
    | *describedby*
    |   A description of the language
    | *subsection*
    |  Functions of the language, e.g., ``p()`` or ``r()``
    | *subsection*
    |  Relationships of the language, e.g., ``increases`` or ``decreases``

    | **Status codes**:
    |   :http:statuscode:`200`
