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
