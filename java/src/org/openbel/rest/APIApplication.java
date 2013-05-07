/**
 * Copyright (C) 2013 Selventa, Inc.
 *
 * This file is part of the BEL Framework REST API.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The BEL Framework REST API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the BEL Framework REST API. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Additional Terms under LGPL v3:
 *
 * This license does not authorize you and you are prohibited from using the
 * name, trademarks, service marks, logos or similar indicia of Selventa, Inc.,
 * or, in the discretion of other licensors or authors of the program, the
 * name, trademarks, service marks, logos or similar indicia of such authors or
 * licensors, in any marketing or advertising materials relating to your
 * distribution of the program or any covered product. This restriction does
 * not waive or limit your obligation to keep intact all copyright notices set
 * forth in the program as delivered to you.
 *
 * If you distribute the program in whole or in part, or any modified version
 * of the program, and you assume contractual liability to the recipient with
 * respect to the program or modified version, then you will indemnify the
 * authors and licensors of the program for any liabilities that these
 * contractual assumptions directly impose on those licensors and authors.
 */
package org.openbel.rest;

import static org.openbel.rest.Util.*;
import org.openbel.rest.common.*;
import org.restlet.*;
import org.restlet.routing.*;

/**
 * This application Restlet manages the BEL REST API resources and services.
 */
public class APIApplication extends Application {

    /**
     * Creates our inbound root Restlet to receive incoming calls.
     * @return {@link Restlet}
     */
    @Override
    public Restlet createInboundRoot() {
        System.out.println("Creating inbound root");
        Router router = new Router(getContext());

        String path = declaredPath(APIRoot.class);
        router.attach(path, APIRoot.class);

        path = declaredPath(VersionsRoot.class);
        router.attach(path, VersionsRoot.class);

        path = declaredPath(V1Root.class);
        router.attach(path, V1Root.class);

        path = declaredPath(AnnotationsRoot.class);
        router.attach(path, AnnotationsRoot.class);

        path = declaredPath(NamespacesRoot.class);
        router.attach(path, NamespacesRoot.class);

        path = declaredPath(CompleteRoot.class);
        router.attach(path, CompleteRoot.class);

        path = declaredPath(Namespace.class);
        router.attach(path, Namespace.class);

        path = declaredPath(NamespaceValue.class);
        router.attach(path, NamespaceValue.class);

        path = declaredPath(LangRoot.class);
        router.attach(path, LangRoot.class);

        path = declaredPath(RelationshipsRoot.class);
        router.attach(path, RelationshipsRoot.class);

        path = declaredPath(Relationships.class);
        router.attach(path, Relationships.class);

        path = declaredPath(SignaturesRoot.class);
        router.attach(path, SignaturesRoot.class);

        path = declaredPath(FunctionsRoot.class);
        router.attach(path, FunctionsRoot.class);

        path = declaredPath(Functions.class);
        router.attach(path, Functions.class);

        path = declaredPath(Signatures.class);
        router.attach(path, Signatures.class);

        return router;
    }
}
