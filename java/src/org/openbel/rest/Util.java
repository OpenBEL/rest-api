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
package org.openbel.rest;

import org.restlet.Request;
import java.net.*;
import static org.openbel.framework.common.enums.FunctionEnum.*;
import org.openbel.framework.common.enums.FunctionEnum;

/**
 * BEL REST API Utilities.
 */
public class Util {
    private final static String[][] METASTR;
    static {
        METASTR = new String[19][];
        METASTR[0] = new String[] { "<", "\\<" };
        METASTR[1] = new String[] { "(", "\\(" };
        METASTR[2] = new String[] { "[", "\\[" };
        METASTR[3] = new String[] { "{", "\\{" };
        METASTR[4] = new String[] { "\\", "\\\\" };
        METASTR[5] = new String[] { "^", "\\^" };
        METASTR[6] = new String[] { "-", "\\-" };
        METASTR[7] = new String[] { "=", "\\=" };
        METASTR[8] = new String[] { "$", "\\$" };
        METASTR[9] = new String[] { "!", "\\!" };
        METASTR[10] = new String[] { "|", "\\|" };
        METASTR[11] = new String[] { "]", "\\]" };
        METASTR[12] = new String[] { "}", "\\}" };
        METASTR[13] = new String[] { ")", "\\)" };
        METASTR[14] = new String[] { "?", "\\?" };
        METASTR[15] = new String[] { "*", "\\*" };
        METASTR[16] = new String[] { "+", "\\+" };
        METASTR[17] = new String[] { ".", "\\." };
        METASTR[18] = new String[] { ">", "\\>" };
    }

    /**
     * Escape any metacharacters contains in {@code regex}.
     *
     * @param regex Regular expression
     * @return String
     */
    public static String escapeRE(String regex) {
        String ret = regex;
        for (final String[] meta : METASTR)
            ret = ret.replace(meta[0], meta[1]);
        return ret;
    }

    /**
     * Converts a URI like {@code "/api/v1"} to the full URL using the current
     * request's context. If no request is associated with the current thread,
     * this method returns the uri as it was passed.
     *
     * If the argument is already a URL, this method returns the uri as it was
     * passed.
     *
     * @param uri URI
     * @return String
     */
    public static String asURL(String uri) {
        try {
            new URL(uri);
            return uri;
        } catch (Exception e) {}
        Request req = Request.getCurrent();
        if (req == null) return uri;
        String root = req.getRootRef().toString();
        return root.concat(uri);
    }

    /**
     * Gets the value of a class annotated with the
     * {@link org.openbel.rest.Path} annotation.
     *
     * @param cls Class
     * @return String; may be null
     */
    public static String declaredPath(Class<?> cls) {
        if (cls.isAnnotationPresent(Path.class)) {
            Path p = cls.getAnnotation(Path.class);
            return p.value();
        }
        return null;
    }

    /**
     * Get a description of a BEL language function.
     *
     * @param f BEL language {@code FunctionEnum}
     * @return String
     */
    public static String description(FunctionEnum f) {
        String ret = null;
        switch (f) {
        case ABUNDANCE:
            ret = "Denotes the abundance of an entity.";
            break;
        case BIOLOGICAL_PROCESS:
            ret = "Denotes a process or population of events.";
            break;
        case CATALYTIC_ACTIVITY:
            ret = "Denotes the frequency or abundance of events where a " +
                  "member acts as an enzymatic catalyst of biochecmial " +
                  "reactions.";
            break;
        case CELL_SECRETION:
            ret = "Denotes the frequency or abundance of events in which " +
                  "members of an abundance move from cells to regions " +
                  "outside of the cells.";
            break;
        case CELL_SURFACE_EXPRESSION:
            ret = "Denotes the frequency or abundance of events in which " +
                  "members of an abundance move to the surface of cells.";
            break;
        case CHAPERONE_ACTIVITY:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member binds to some substrate and acts as a chaperone " +
                  "for the substrate.";
            break;
        case COMPLEX_ABUNDANCE:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member binds to some substrate and acts as a chaperone " +
                  "for the substrate.";
            break;
        case COMPOSITE_ABUNDANCE:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member binds to some substrate and acts as a chaperone " +
                  "for the substrate.";
            break;
        case DEGRADATION:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member binds to some substrate and acts as a chaperone " +
                  "for the substrate.";
            break;
        case FUSION:
            ret = "Specifies the abundance of a protein translated from the " +
                  "fusion of a gene.";
            break;
        case GENE_ABUNDANCE:
            ret = "Specifies the abundance of a protein translated from the " +
                  "fusion of a gene.";
            break;
        case GTP_BOUND_ACTIVITY:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member of a G-protein abundance is GTP-bound.";
            break;
        case KINASE_ACTIVITY:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member acts as a kinase, performing enzymatic phosphoryl" +
                  "ation of a substrate.";
            break;
        case MICRORNA_ABUNDANCE:
            ret = "Denotes the abundance of a processed, functional microRNA.";
            break;
        case MOLECULAR_ACTIVITY:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member acts as a causal agent at the molecular scale.";
            break;
        case PATHOLOGY:
            ret = "Denotes a disease or pathology process.";
            break;
        case PEPTIDASE_ACTIVITY:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member acts to cleave a protein.";
            break;
        case PHOSPHATASE_ACTIVITY:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member acts as a phosphatase.";
            break;
        case PRODUCTS:
            ret = "Denotes the products of a reaction.";
            break;
        case PROTEIN_ABUNDANCE:
            ret = "Denotes the abundance of a protein.";
            break;
        case PROTEIN_MODIFICATION:
            ret = "Denotes a covalently modified protein abundance.";
            break;
        case REACTANTS:
            ret = "Denotes the reactants of a reaction.";
            break;
        case REACTION:
            ret = "Denotes the frequency or abundance of events in a " +
                  "reaction.";
            break;
        case RIBOSYLATION_ACTIVITY:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member acts to perform post-translational modification " +
                  "of proteins.";
            break;
        case RNA_ABUNDANCE:
            ret = "Denotes the abundance of a gene.";
            break;
        case SUBSTITUTION:
            ret = "Indicates the abundance of proteins with amino acid " +
                  "substitution sequence.";
            break;
        case TRANSCRIPTIONAL_ACTIVITY:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member directly acts to control transcription of genes.";
            break;
        case TRANSLOCATION:
            ret = "Denotes the frequency or abundance of events in which " +
                  "members move between locations.";
            break;
        case TRANSPORT_ACTIVITY:
            ret = "Denotes the frequency or abundance of events in which a " +
                  "member directs acts to enable the directed movement of " +
                  "substances into, out of, within, or between cells.";
            break;
        case TRUNCATION:
            ret = "Indicates an abundance of proteins with truncation " +
                  "sequence variants.";
            break;
        }
        return ret;
    }

}
