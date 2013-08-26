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

import static org.openbel.framework.common.enums.RelationshipType.*;
import static org.openbel.rest.Util.*;
import static org.openbel.rest.common.Objects.*;
import static java.net.URLDecoder.*;
import org.openbel.framework.common.enums.RelationshipType;
import static org.openbel.rest.common.Objects.*;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.openbel.rest.Path;
import org.restlet.data.Status;
import java.util.*;
import java.io.*;

@Path("/api/v1/lang/relationships/{relationship}")
public class Relationships extends ServerResource {

    @Get("json")
    public Representation _get() {
        String relationship = getAttribute("relationship");
        try {
            relationship = decode(relationship, "UTF-8");
        } catch (UnsupportedEncodingException e) {}
        RelationshipType r = fromString(relationship);
        if (r == null) r = fromAbbreviation(relationship);
        if (r == null) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return null;
        }
        String name = r.getDisplayValue();
        String abbrev = r.getAbbreviation();
        Objects.Relationship objr = new Objects.Relationship(name, abbrev);

        Map<String, Boolean> metadata = new HashMap<>();
        objr.put("metadata", metadata);
        metadata.put("causal", r.isCausal());
        metadata.put("correlative", r.isCorrelative());
        metadata.put("decreasing", r.isDecreasing());
        metadata.put("direct", r.isDirect());
        metadata.put("directed", r.isDirected());
        metadata.put("genomic", r.isGenomic());
        metadata.put("increasing", r.isIncreasing());
        metadata.put("indirect", r.isIndirect());
        metadata.put("listable", r.isListable());
        metadata.put("self", r.isSelf());

        objr.put("description", description(r));
        String path = declaredPath(RelationshipsRoot.class);
        objr.addLink("self", urlify(path, name));
        RelationshipsRoot.linkResource(objr);
        return objr.json();
    }

}
