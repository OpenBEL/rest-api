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
