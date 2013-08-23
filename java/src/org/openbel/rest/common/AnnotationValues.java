/**
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

import static java.lang.String.format;
import static org.openbel.rest.common.Objects.*;
import static org.openbel.rest.Util.*;
import static org.openbel.rest.main.*;
import org.openbel.rest.common.Objects;
import org.jongo.*;
import org.openbel.rest.Path;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.data.Status;
import java.util.*;

@Path("/api/v1/annotations/{keyword}/values")
public class AnnotationValues extends ServerResource {
    private static final String FIND_RSRC;
    private static final String FIND_VALUES;
    static {
        FIND_RSRC = "{keyword: '%s'}";
        FIND_VALUES = "{annometa-id: #}";
    }

    @Get("json")
    public Representation _get() {
        String keyword = getAttribute("keyword");
        String query = format(FIND_RSRC, keyword);
        Map<?, ?> anno = $annotations.findOne(query).as(Map.class);
        if (anno == null) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return null;
        }

        String name = (String) anno.get("name");
        String kword = (String) anno.get("keyword");
        String type = (String) anno.get("type");
        String usage = (String) anno.get("usage");
        String desc = (String) anno.get("description");
        Objects.Annotation obja = new Objects.Annotation(name, kword, type);
        obja.setUsage(usage);
        obja.setDescription(desc);
        Object _id = anno.get("_id");

        Find find = $annovalues.find(FIND_VALUES, _id);
        List<String> values = new LinkedList<>();
        for (Map<?, ?> map : find.as(Map.class)) {
            values.add((String) map.get("val"));
        }
        obja.put("values", values);

        // links
        String path = declaredPath(Objects.Annotations.class);
        obja.addLink("self", urlify(path, keyword, "values"));
        obja.addLink("related", urlify(path, keyword));
        return obja.json();
    }

}
