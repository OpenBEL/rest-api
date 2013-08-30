BEL Framework REST API
======================

**For a more complete guide to using the BEL Framework, we encourage you to
check out the** `OpenBEL Wiki`_.

The OpenBEL Framework is an open-platform technology for managing, publishing,
and using biological knowledge represented using the Biological Expression
Language (BEL). BEL is an easy to understand, computable format for representing
biological knowledge. The OpenBEL Framework is specifically designed to
overcome many of the challenges associated with capturing, integrating, and
storing biological knowledge within an organization, and sharing the knowledge
across the organization and between business partners. The framework provides
mechanisms for: 

 #. Capture and management of biological knowledge
 #. Integration of knowledge from multiple sources
 #. Knowledge representation in a standard and open format
 #. Creation of custom, computable biological networks from captured
    knowledge
 #. Enabling applications to query knowledge networks using web and Java APIs

Central to the design of the framework is the ability to integrate knowledge
across different representational vocabularies and ontologies. This allows
organizations to combine knowledge from disparate sources into centralized
knowledge repositories. The combined knowledge can be made available to a
variety of decision support and analytical applications through a standardized
set of computable networks and APIs.

.. contents::

The OpenBEL Ecosystem
---------------------

Various smaller projects are connected with the framework. Each project is
intended on serving a particular purpose.

`OpenBEL Discussion Group`_
  This group is used to discuss OpenBEL technologies, the BEL language, and
  anything relevant to the OpenBEL ecosystem as a whole.

  You can subscribe to these announcements by visiting the link, or by sending
  an email to ``openbel-discuss+subscribe@googlegroups.com`` with the subject
  ``subscribe``.

`OpenBEL Announcement Group`_
  This group is used to issue announcements related to OpenBEL.

  You can subscribe to these announcements by visting the link, or by sending an
  email to ``openbel-announce+subscribe@googlegroups.com`` with the subject
  ``subscribe``.

`freenode IRC network`_
  We maintain the ``#openbel`` channel on the freenode network.

  To connect to freenode, you can use ``chat.freenode.net`` as an IRC server.
  On Linux and Windows, you can use `XChat`_ as an IRC client. On Mac OS X,
  there is `Xirc`_.

`openbel-framework-resources`_
  The framework's resources provide a set of files derived from biological and
  chemical ontologies. These resources are used to better describe biological
  statements and their context.

`openbel-framework-examples`_
  This repository is for helping developers get started with different uses of
  the framework. It contains a number of examples in various programming
  languages.

`Cytoscape Plugins`_
  The `Cytoscape`_ plugins enable Cytoscape to access and manipulate the OpenBEL
  Framework knowledge assembly models using the framework's web service API.

`OpenBEL Eclipse Repository`_
  The OpenBEL Eclipse p2 repository. It currently holds the stable and unstable
  repositories of the OpenBEL Workbench.

`BEL Editor`_
  The BEL Editor enables the BEL language and the framework to be used in
  `Eclipse`_ - from the syntax and semantics of the BEL language to the
  compilation of BEL knowledge into knowledge assembly models.

`REST API`_
  Provides a RESTful API for the BEL language and framework.

.. _OpenBEL Discussion Group: https://groups.google.com/forum/#!forum/openbel-discuss
.. _OpenBEL Announcement Group: https://groups.google.com/forum/#!forum/openbel-announce
.. _openbel-framework-resources: https://github.com/OpenBEL/openbel-framework-resources
.. _openbel-framework-examples: https://github.com/OpenBEL/openbel-framework-examples
.. _Cytoscape Plugins: https://github.com/OpenBEL/Cytoscape-Plugins#readme
.. _OpenBEL Eclipse Repository: https://github.com/OpenBEL/eclipse
.. _BEL Editor: https://github.com/OpenBEL/bel-editor
.. _REST API: https://github.com/OpenBEL/rest-api
.. _Cytoscape: http://www.cytoscape.org/
.. _Eclipse: http://eclipse.org
.. _freenode IRC network: http://www.freenode.net/
.. _XChat: http://xchat.org/
.. _Xirc: http://www.aquaticx.com/
.. _OpenBEL Wiki: http://wiki.openbel.org

Requirements
------------

 * Java 7: http://java.com/en/download/index.jsp
 * Clojure 1.5: http://clojure.org/download
 * Python 3.3: http://www.python.org/download/
 * Apache Buildr: http://buildr.apache.org/download.html
 * MongoDB 2.4.3: http://www.mongodb.org/downloads
 * Leiningen: https://github.com/technomancy/leiningen
 * Linux or OS X

Use
---

Prior to use, the BEL REST API must be built and have BEL data loaded into it.
Follow these steps:

 #. ``scripts/build.sh``

 #. ``scripts/load.sh``

 #. ``scripts/mongo-check.sh``

Once these three steps have completed, use ``scripts/run.sh`` to run.

Alternatively, use ``go.sh`` as a frontend to the scripts.

API Documentation
-----------------

Nothing here yet.

License
-------

Apache License, Version 2.0.

Further Reference
-----------------

 * BEL Portal: http://openbel.org
 * OpenBEL Wiki: http://wiki.openbel.org
 * Mailing list: https://groups.google.com/forum/#!forum/openbel-discuss
 * OpenBEL build server: http://build.openbel.org
 * GitHub: https://github.com/OpenBEL
 * Freecode: http://freecode.com/projects/openbel-framework

