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

import static org.openbel.framework.common.enums.FunctionEnum.*;
import static org.openbel.framework.common.enums.RelationshipType.*;
import static org.openbel.rest.Util.*;
import static org.openbel.rest.common.Objects.*;
import static java.util.Collections.*;
import org.openbel.framework.common.enums.RelationshipType;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Get;
import org.openbel.rest.Path;
import java.util.*;

@Path("/api/v1/lang/relationships")
public class RelationshipsRoot extends ServerResource {
    private static final List<String> RESOURCES;
    private static final String START;
    private static final String END;
    private static final Objects.Relationships RELATIONSHIPS;
    private static final String MY_PATH;
    static {
        RELATIONSHIPS = new Objects.Relationships();
        RESOURCES = new ArrayList<>();
        MY_PATH = declaredPath(RelationshipsRoot.class);
        for (RelationshipType r : RelationshipType.values()) {
            switch (r) {
            case ACTS_IN:
            case INCLUDES:
            case TRANSLOCATES:
            case HAS_PRODUCT:
            case REACTANT_IN:
            case HAS_MODIFICATION:
            case HAS_VARIANT:
                continue;
            }
            String name = r.getDisplayValue();
            String abbrev = r.getAbbreviation();
            Objects.Relationship objr = new Objects.Relationship(name, abbrev);
            objr.put("description", description(r));
            objr.addLink("self", urlify(MY_PATH, name));
            RELATIONSHIPS.addRelationship(objr);
            RESOURCES.add(name);
        }
        sort(RESOURCES);
        START = RESOURCES.get(0);
        END = RESOURCES.get(RESOURCES.size() - 1);
        RELATIONSHIPS.addLink("start", urlify(MY_PATH, START));
        RELATIONSHIPS.addLink("end", urlify(MY_PATH, END));
    }

    @Get("json")
    public Representation _get() {
        return RELATIONSHIPS.json();
    }

    static void linkResource(Relationship resource) {
        resource.addLink("index", RelationshipsRoot.class);
        resource.addLink("first", urlify(MY_PATH, START));
        int i = RESOURCES.indexOf(resource.name);
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
