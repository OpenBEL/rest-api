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
