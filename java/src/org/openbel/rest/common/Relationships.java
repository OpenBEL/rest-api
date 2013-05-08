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
        objr.addLink("self", path + "/" + name);
        RelationshipsRoot.linkResource(objr);
        return objr.json();
    }

}