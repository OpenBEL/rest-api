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
import static org.openbel.rest.Util.*;
import static java.util.Collections.*;
import org.openbel.rest.common.Objects;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.openbel.framework.common.lang.Function;
import org.openbel.framework.common.enums.FunctionEnum;
import org.openbel.rest.Path;
import java.util.*;

@Path("/api/v1/lang/functions")
public class FunctionsRoot extends ServerResource {
    private static final List<String> RESOURCES;
    private static final String START;
    private static final String END;
    private static final Objects.Functions FUNCTIONS;
    private static final String MY_PATH;
    static {
        FUNCTIONS = new Objects.Functions();
        RESOURCES = new ArrayList<>();
        MY_PATH = declaredPath(FunctionsRoot.class);
        for (FunctionEnum e : FunctionEnum.values()) {
            if (e == LIST) continue;
            Function f = e.getFunction();
            String name = e.getDisplayValue();
            String abbrev = e.getAbbreviation();
            Objects.Function objf = new Objects.Function(name, abbrev);
            objf.put("description", description(e));
            String path = declaredPath(Objects.Signatures.class);
            objf.addLink("related", urlify(path, name));
            objf.addLink("self", urlify(MY_PATH, name));
            FUNCTIONS.addFunction(objf);
            RESOURCES.add(name);
        }
        sort(RESOURCES);
        START = RESOURCES.get(0);
        END = RESOURCES.get(RESOURCES.size() - 1);
        FUNCTIONS.addLink("start", urlify(MY_PATH, START));
        FUNCTIONS.addLink("end", urlify(MY_PATH, END));
    }

    @Get("json")
    public Representation _get() {
        return FUNCTIONS.json();
    }

    static void linkResource(Objects.Function resource) {
        resource.addLink("index", FunctionsRoot.class);
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
