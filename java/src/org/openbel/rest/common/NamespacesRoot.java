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
            objn.addLink("self", urlify(MY_PATH, name));
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
