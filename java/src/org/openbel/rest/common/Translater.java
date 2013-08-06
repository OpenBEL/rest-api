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
import java.io.*;
import java.util.*;
import java.util.regex.*;

@Path("/api/v1/lang/translater")
public class Translater extends ServerResource {
    private final static String[][] REGEXES;
    static {
        List<List<String>> strs = new ArrayList<>();
        try {
            File tsv = new File($top, "BEL_to_English.tsv");
            FileReader fr = new FileReader(tsv);
            BufferedReader br = new BufferedReader(fr);
            String input;
            while ((input = br.readLine()) != null) {
                if ("".equals(input)) continue;
                if (input.charAt(0) == '#') continue;
                StringTokenizer st = new StringTokenizer(input, "\t");
                assert st.countTokens() == 2;
                String re = st.nextToken();
                try {
                    Pattern.compile(re);
                } catch (Exception e) {
                    System.out.println(re);
                    System.out.println(e);
                    System.out.println();
                }
                String replacement = st.nextToken();
                List<String> entry = new ArrayList<>();
                entry.add(re);
                entry.add(replacement);
                strs.add(entry);
            }
            br.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
            System.exit(1);
        }
        REGEXES = new String[strs.size()][];
        for (int i = 0; i < REGEXES.length; i++) {
            List<String> entry = strs.get(i);
            String[] rerepPair = new String[] { entry.get(0), entry.get(1) };
            REGEXES[i] = rerepPair;
        }
    }

    @Post("txt")
    public String _post(Representation body) {
        if (body == null) return "";
        String txt = textify(body);
        if ("".equals(txt) || txt == null) return "";

        String ret = "";
        for (int i = 0; i < REGEXES.length; i++) {
            Pattern p = Pattern.compile(REGEXES[i][0]);
            String repl = REGEXES[i][1];
            Matcher m = p.matcher(txt);
            if (m.find()) {
                txt = m.replaceFirst(repl);
            }
            txt = txt.trim();
            // Keep going until each iteration
            if ((i + 1) == REGEXES.length) {
                if (!ret.equals(txt)) i = 0;
                ret = txt;
                continue;
            }
        }
        return txt;
    }

}
