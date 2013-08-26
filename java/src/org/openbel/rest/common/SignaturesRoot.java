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

import static org.openbel.rest.common.Objects.Signatures;
import org.openbel.rest.common.Objects;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.openbel.framework.common.lang.Function;
import org.openbel.framework.common.lang.Signature;
import org.openbel.framework.common.enums.FunctionEnum;
import org.openbel.rest.Path;

@Path("/api/v1/lang/functions/signatures")
public class SignaturesRoot extends ServerResource {
    private static final Signatures SIGNATURES;
    static {
        SIGNATURES = new Signatures();
        for (FunctionEnum e : FunctionEnum.values()) {
            Function f = e.getFunction();
            String name = e.getDisplayValue();
            String abbrev = e.getAbbreviation();
            Objects.Function objf = new Objects.Function(name, abbrev);
            for (Signature s : f.getSignatures()) {
                String value = s.getValue();
                String numArgs = s.getNumberOfArguments();
                String returnType = s.getReturnType().toString();
                Objects.Signature objs = new Objects.Signature(value, numArgs, returnType);
                objf.addSignature(objs);
            }
            SIGNATURES.addFunction(objf);
        }
    }

    @Get("json")
    public Representation _get() {
        return SIGNATURES.json();
    }

}
