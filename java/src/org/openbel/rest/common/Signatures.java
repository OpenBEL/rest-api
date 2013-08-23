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

import static org.openbel.framework.common.enums.FunctionEnum.*;
import static org.openbel.rest.Util.*;
import org.openbel.rest.common.Objects;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.openbel.framework.common.lang.Function;
import org.openbel.framework.common.lang.Signature;
import org.openbel.framework.common.enums.FunctionEnum;
import org.openbel.rest.Path;
import org.restlet.data.Status;

@Path("/api/v1/lang/functions/signatures/{function}")
public class Signatures extends ServerResource {

    @Get("json")
    public Representation _get() {
        String function = getAttribute("function");
        FunctionEnum f = fromString(function);
        if (f == null) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return null;
        }

        String name = f.getDisplayValue();
        String abbrev = f.getAbbreviation();
        Objects.Function objf = new Objects.Function(name, abbrev);
        for (Signature s : f.getFunction().getSignatures()) {
            String value = s.getValue();
            String numArgs = s.getNumberOfArguments();
            String returnType = s.getReturnType().toString();
            Objects.Signature objs = new Objects.Signature(value, numArgs, returnType);
            objf.addSignature(objs);
        }
        objf.put("description", description(f));

        // links
        String path = declaredPath(Objects.Signatures.class);
        objf.addLink("self", urlify(path, function));
        objf.addLink("enclosure", path);
        path = declaredPath(Objects.Functions.class);
        objf.addLink("related", urlify(path, function));
        return objf.json();
    }

}
