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
import static org.openbel.framework.common.bel.parser.BELParser.*;
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

@Path("/api/v1/lang/validater")
public class Validater extends ServerResource {

    @Post("json")
    public Representation _post(Representation body) {
        Map<String, Object> map = mapify(body);
        if (map == null) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return null;
        }

        Object term_obj = map.get("term");
        Object stmt_obj = map.get("statement");
        Object script_obj = map.get("script");
        if (term_obj == null && stmt_obj == null && script_obj == null) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return null;
        }

        String term_str = cast(term_obj, String.class);
        String stmt_str = cast(stmt_obj, String.class);
        String script_str = cast(script_obj, String.class);

        Validations objv = new Validations();
        Validation v;

        if (term_str != null) {
            Term term;
            try {
                term = parseTerm(term_str);
            } catch (Exception e) {
                term = null;
            }
            if (term == null) v = new Validation(false);
            else v = new Validation(true);
            objv.addTermValidation(v);
        }

        if (stmt_str != null) {
            Statement stmt;
            try {
                stmt = parseStatement(stmt_str);
            } catch (Exception e) {
                stmt = null;
            }
            if (stmt == null) v = new Validation(false);
            else v = new Validation(true);
            objv.addStatementValidation(v);
        }

        if (script_str != null) {
            BELParseResults rslts = parse(script_str);
            List<BELParseErrorException> errors = rslts.getSyntaxErrors();
            List<BELParseWarningException> warns = rslts.getSyntaxWarnings();
            ScriptValidation sv;
            if (errors.size() != 0) sv = new ScriptValidation(false);
            else sv = new ScriptValidation(true);
            for (BELParseErrorException e : errors)
                sv.addSyntaxError(e.getMessage());
            for (BELParseWarningException e : warns)
                sv.addSyntaxWarning(e.getMessage());
            objv.addScriptValidation(sv);
        }

        return objv.json();
    }

}
