/*
 *  Copyright 2013 OpenBEL Consortium
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.openbel.rest;

import static org.openbel.rest.Util.*;
import org.openbel.rest.common.*;
import org.restlet.*;
import org.restlet.routing.*;

/*
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

        path = declaredPath(CompilerRoot.class);
        router.attach(path, CompilerRoot.class);

        path = declaredPath(Validater.class);
        router.attach(path, Validater.class);

        path = declaredPath(TermValidater.class);
        router.attach(path, TermValidater.class);

        path = declaredPath(StatementValidater.class);
        router.attach(path, StatementValidater.class);

        path = declaredPath(ScriptValidater.class);
        router.attach(path, ScriptValidater.class);

        path = declaredPath(AnnotationsRoot.class);
        router.attach(path, AnnotationsRoot.class);

        path = declaredPath(Annotations.class);
        router.attach(path, Annotations.class);

        path = declaredPath(AnnotationValues.class);
        router.attach(path, AnnotationValues.class);

        path = declaredPath(NamespacesRoot.class);
        router.attach(path, NamespacesRoot.class);

        path = declaredPath(Namespaces.class);
        router.attach(path, Namespaces.class);

        path = declaredPath(NamespaceValues.class);
        router.attach(path, NamespaceValues.class);

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

        path = declaredPath(Translation.class);
        router.attach(path, Translation.class);

        path = declaredPath(NamespaceKeywordCompletion.class);
        router.attach(path, NamespaceKeywordCompletion.class);

        path = declaredPath(NamespaceValueCompletion.class);
        router.attach(path, NamespaceValueCompletion.class);

        path = declaredPath(AnnotationKeywordCompletion.class);
        router.attach(path, AnnotationKeywordCompletion.class);

        path = declaredPath(AnnotationValueCompletion.class);
        router.attach(path, AnnotationValueCompletion.class);

        return router;
    }
}
