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
package org.openbel.rest.common;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.openbel.rest.Path;

import java.util.Map;

@Path("/api/v1/namespaces/{keyword}/{value}")
public class NamespaceValue extends ServerResource {

    class Root {
        private String root;

        Root() {
            root = "namespace_value";
        }
        public String getRoot() {
            return root;
        }
    }

    @Get("json")
    public Root _get() {
        return new Root();
    }

}
