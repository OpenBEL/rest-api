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
            objf.addLink("related", path + "/" + name);
            objf.addLink("self", MY_PATH + "/" + name);
            FUNCTIONS.addFunction(objf);
            RESOURCES.add(name);
        }
        sort(RESOURCES);
        START = RESOURCES.get(0);
        END = RESOURCES.get(RESOURCES.size() - 1);
        FUNCTIONS.addLink("start", MY_PATH + "/" + START);
        FUNCTIONS.addLink("end", MY_PATH + "/" + END);
    }

    @Get("json")
    public Representation _get() {
        return FUNCTIONS.json();
    }

    static void linkResource(Objects.Function resource) {
        resource.addLink("index", FunctionsRoot.class);
        resource.addLink("first", MY_PATH + "/" + START);
        resource.addLink("last", MY_PATH + "/" + END);
        int i = RESOURCES.indexOf(resource.name);
        if (i > 0) {
            String prev = RESOURCES.get(i - 1);
            resource.addLink("prev", MY_PATH + "/" + prev);
        }
        if ((i + 1) < (RESOURCES.size())) {
            String next = RESOURCES.get(i + 1);
            resource.addLink("next", MY_PATH + "/" + next);
        }
    }

}
