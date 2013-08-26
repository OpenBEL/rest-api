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

@Path("/api/v1/lang/translation")
public class Translation extends ServerResource {
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
