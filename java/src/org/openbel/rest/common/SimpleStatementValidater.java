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

import static java.lang.String.format;
import static org.openbel.rest.common.Objects.*;
import static org.openbel.rest.Util.*;
import static org.openbel.rest.main.*;
import static org.openbel.framework.common.bel.parser.BELParser.*;
import org.openbel.framework.common.enums.RelationshipType;
import org.openbel.rest.common.Objects;
import org.jongo.*;
import org.openbel.rest.Path;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.openbel.framework.common.model.*;
import org.openbel.framework.common.bel.parser.BELParseResults;
import org.openbel.bel.model.*;
import org.restlet.representation.Representation;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.data.Status;
import java.util.*;

@Path("/api/v1/lang/validater/statement/simple")
public class SimpleStatementValidater extends ServerResource {

    @Post("txt")
    public Representation _post(Representation body) {
        String txt = textify(body);
        if (txt == null) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return null;
        }

        Validations objv = new Validations();
        Validation v;

        // return invalid statement if subject-relationship-object
        // is detected in the tokenized form
        boolean found = false;
        for (RelationshipType rel : RelationshipType.values()) {
            // try tokenizing by display value first
            String dispval = rel.getDisplayValue();
            dispval = " " + dispval + " ";
            StringTokenizer st = new StringTokenizer(txt, dispval);
            if (st.countTokens() == 3) {
                // subject relationship object match
                found = true;
                break;
            }

            // fallback to tokenizing by abbreviation
            String abbrev = rel.getAbbreviation();
            if (abbrev == null) continue;
            abbrev = " " + abbrev + " ";
            st = new StringTokenizer(txt, abbrev);
            if (st.countTokens() == 3) {
                // subject relationship object match
                found = true;
                break;
            }
        }
        if (found) {
            v = new Validation(false);
            objv.addStatementValidation(v);
            return objv.json();
        }

        Statement stmt;
        try {
            stmt = parseStatement(txt);
        } catch (Exception e) {
            stmt = null;
        }
        if (stmt == null) v = new Validation(false);
        else v = new Validation(true);
        objv.addStatementValidation(v);

        return objv.json();
    }

}
