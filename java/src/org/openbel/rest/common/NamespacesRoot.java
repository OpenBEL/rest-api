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

import static java.util.Collections.*;
import static org.openbel.rest.Util.*;
import static org.openbel.rest.main.*;
import org.openbel.rest.common.Objects;
import org.jongo.*;
import org.openbel.rest.Path;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import java.util.*;

@Path("/api/v1/namespaces")
public class NamespacesRoot extends ServerResource {
	private static final List<String> RESOURCES;
	private static final String ROOT_FIND;
	private static final String ROOT_PROJECTION;
    private static final String START;
    private static final String END;
    private static final String MY_PATH;
	static {
		ROOT_FIND = "{}";
		ROOT_PROJECTION = "{description: 0}";
		RESOURCES = new ArrayList<>();
        MY_PATH = declaredPath(NamespacesRoot.class);
    	Find find = $namespaces.find(ROOT_FIND);
        for (Map<?, ?> map : find.as(Map.class))
        	RESOURCES.add((String) map.get("keyword"));
        sort(RESOURCES);
        START = RESOURCES.get(0);
        END = RESOURCES.get(RESOURCES.size() - 1);
	}

    @Get("json")
    public Representation _get() {
    	Objects.Namespaces ret = new Objects.Namespaces();
    	Find find = $namespaces.find(ROOT_FIND).projection(ROOT_PROJECTION);
        for (Map<?, ?> map : find.as(Map.class)) {
        	String name = (String) map.get("name");
        	String kword = (String) map.get("keyword");
        	Objects.Namespace objn = new Objects.Namespace(name, kword, null);
        	ret.addNamespace(objn);
            objn.addLink("related", urlify(MY_PATH, kword, "values"));
            objn.addLink("self", urlify(MY_PATH, kword));
        }
        ret.addLink("start", urlify(MY_PATH, START));
        ret.addLink("end", urlify(MY_PATH, END));
        return ret.json();
    }

    static void linkResource(Objects.Namespace resource) {
        resource.addLink("index", NamespacesRoot.class);
        resource.addLink("first", urlify(MY_PATH, START));
        int i = RESOURCES.indexOf(resource.keyword);
        if ((i + 1) < (RESOURCES.size())) {
            String next = RESOURCES.get(i + 1);
            resource.addLink("next", urlify(MY_PATH, next));
        }
        if (i > 0) {
            String prev = RESOURCES.get(i - 1);
            resource.addLink("prev", urlify(MY_PATH, prev));
        }
        resource.addLink("last", urlify(MY_PATH, END));
    }

}
