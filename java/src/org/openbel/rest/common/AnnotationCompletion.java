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

import static org.openbel.rest.common.Objects.*;
import static org.openbel.rest.main.*;
import static org.openbel.rest.Util.*;
import static java.util.regex.Pattern.*;
import static java.lang.String.format;
import org.jongo.*;
import java.util.*;
import java.util.regex.*;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.data.Status;
import org.openbel.rest.Path;

@Path("/api/v1/completion/annotation/{value}")
public class AnnotationCompletion extends ServerResource {
    private static final String ALT_URL;
    private static final String FIND_ONE;
    private static final String FIND_VALS;
    static {
        ALT_URL = "/api/v1/completion/annotation/";
        FIND_ONE = "{keyword: '%s'}";
        FIND_VALS = "{annometa-id:#, norm:#}";
    }

    @Get("json")
    public Representation _get() {
        /*
        String value = getAttribute("value");

        String query = format(FIND_ONE, keyword);
        @SuppressWarnings("unchecked")
        Map<?, ?> ao = $annotations.findOne(query).as(Map.class);
        if (ao == null) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return null;
        }

        String input = format("^%s", escapeRE(value));
        Pattern ptrn = compile(input.toLowerCase());
        Find find = $annovalues.find(FIND_VALS, (ao.get("_id")), ptrn);

        List<String> rslts = new ArrayList<>();
        for (Map<?, ?> map : find.as(Map.class)) {
            rslts.add((String) map.get("val"));
        }
        if (rslts.size() == 0) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return null;
        }

        if (rslts.size() == 1) {
            AOValueCompletion ret = new AOValueCompletion();
            ret.addValue(rslts.get(0));
            ret.addLink("self", ALT_URL + keyword + "/" + value);
            return ret.json();
        }

        AOValueCompletion ret = new AOValueCompletion();
        for (String rslt : rslts) {
            ret.addLink("result", ALT_URL + keyword + "/" + rslt);
            ret.addValue(rslt);
        }
        ret.addLink("self", ALT_URL + keyword + "/" + value);
        setStatus(Status.REDIRECTION_MULTIPLE_CHOICES);
        return ret.json();
        */
        return null;
    }

}
