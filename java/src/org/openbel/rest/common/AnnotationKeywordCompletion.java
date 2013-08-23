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

@Path("/api/v1/completion/annotation/{keyword}")
public class AnnotationKeywordCompletion extends ServerResource {
    private static final String ALT_URL = "/api/v1/completion/annotation/";
    private static final String FIND;
    static {
        FIND = "{norm:#}";
    }

    @Get("json")
    public Representation _get() {
        String keyword = getAttribute("keyword");
    	String input = format("^%s", escapeRE(keyword));
    	Pattern ptrn = compile(input.toLowerCase());
        Find find = $annotations.find(FIND, ptrn);

        List<String> rslts = new ArrayList<>();
        for (Map<?, ?> map : find.as(Map.class)) {
            rslts.add((String) map.get("keyword"));
        }

        if (rslts.size() == 1) {
            AOKeywordCompletion ret = new AOKeywordCompletion();
            ret.addValue(rslts.get(0));
            ret.addLink("self", ALT_URL + keyword);
            return ret.json();
        }

        AOKeywordCompletion ret = new AOKeywordCompletion();
        for (String rslt : rslts) {
            ret.addLink("result", ALT_URL + rslt);
            ret.addValue(rslt);
        }
        ret.addLink("self", ALT_URL + keyword);
        setStatus(Status.REDIRECTION_MULTIPLE_CHOICES);
        return ret.json();
    }

}
