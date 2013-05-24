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

import static org.openbel.framework.common.enums.FunctionEnum.*;
import static org.openbel.framework.common.enums.RelationshipType.*;
import static java.lang.System.*;
import org.restlet.Request;
import java.net.*;
import java.util.*;
import java.io.IOException;
import org.openbel.framework.common.enums.FunctionEnum;
import org.restlet.representation.Representation;
import org.openbel.framework.common.enums.RelationshipType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.util.Series;

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
     * Cast {@code o} to {@code T}, returning {@code null} if not possible.
     *
     * @param o Object to cast
     * @param cls Class to cast to
     */
    public static <T> T cast(Object o, Class<T> cls) {
        if (o == null || cls == null) return null;
        if (cls.isAssignableFrom(o.getClass())) return cls.cast(o);
        return null;
    }

    /**
     * Inserts {@code "/"} between each element.
     *
     * @param elements
     * @return String in the form {@code elem1/elem2/elem3/.../elemN}
     */
    public static String urlify(String... elements) {
        if (elements == null) return null;
        final StringBuilder bldr = new StringBuilder();
        for (final String elem : elements) {
            if (bldr.length() != 0) bldr.append("/");
            bldr.append(elem);
        }
        return bldr.toString();
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
        Map<String, Object> attrs = req.getAttributes();
        Series<?> series = (Series<?>) attrs.get("org.restlet.http.headers");
        String realURI = series.getFirstValue("X-Real-URI");
        if (realURI != null) return realURI.concat(uri);
        String root = req.getRootRef().toString();
        return root.concat(uri);
    }

    /**
     * Get a map from a representation.
     *
     * @param r Representation
     * @return {@link Map}; may be null if conversion fails
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> mapify(Representation r) {
        try {
            return new JacksonRepresentation<Map>(r, Map.class).getObject();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get text from a representation.
     *
     * @param r Representation
     * @return {@link String}; may be null if conversion fails
     */
    public static String textify(Representation r) {
        try {
            return r.getText();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get the value of a class annotated with the
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

    public static String description(RelationshipType r) {
        String ret = null;
        switch (r) {
        case ANALOGOUS:
            ret = "For terms A and B, A analogousTo B indicates that A " +
                  "and B represent abundances or molecular activities in " +
                  "different species which function in a similar manner.";
            break;
        case ASSOCIATION:
            ret = "For terms A and B, A association B or A -- B indicates " +
                  "that A and B are associated in an unspecified manner. " +
                  "This relationship is used when not enough information " +
                  "about the association is available to describe it using " +
                  "more specific relationships, like increases or " +
                  "positiveCorrelation.";
            break;
        case BIOMARKER_FOR:
            ret = "For term A and process term P, A biomarkerFor P " +
                  "indicates that changes in or detection of A is used in " +
                  "some way to be a biomarker for pathology or biological " +
                  "process P.";
            break;
        case CAUSES_NO_CHANGE:
            ret = "For terms A and B, A causesNoChange B indicates that B " +
                  "was observed not to change in response to changes in A. " +
                  "Statements using this relationship correspond to cases " +
                  "where explicit measurement of B demonstrates lack of " +
                  "significant change, not for cases where the state of B " +
                  "is unknown.";
            break;
        case DECREASES:
            ret = "For terms A and B, A decreases B or A -| B indicates " +
                  "that increases in A have been observed to cause " +
                  "decreases in B. A decreases B also represents cases " +
                  "where decreases in A have been observed to cause " +
                  "increases in B, for example, in recording the results of " +
                  "gene deletion or other inhibition experiments. A is a " +
                  "BEL Term and B is either a BEL Term or a BEL Statement. " +
                  "The relationship does not indicate that the changes in A " +
                  "are either necessary for changes in B, nor does it " +
                  "indicate that changes in A are sufficient to cause " +
                  "changes in B.";
            break;
        case DIRECTLY_DECREASES:
            ret = "For terms A and B, A directlyDecreases B or A =| B " +
                  "indicates A decreases B and that the mechanism of the " +
                  "causal relationship is based on physical interaction of " +
                  "entities related to A and B. This is a direct version of " +
                  "the decreases relationship.";
            break;
        case DIRECTLY_INCREASES:
            ret = "For terms A and B, A directlyIncreases B or A => B " +
                  "indicate A increases B and that the mechanism of the " +
                  "causal relationship is based on physical interaction of " +
                  "entities related to A and B. This is a direct version of " +
                  "the increases relationship.";
            break;
        case HAS_COMPONENT:
            ret = "For complexAbundance term A and abundance term B, A " +
                  "hasComponent B designates B as a component of A, that " +
                  "complexes that are instances of A have instances of B as " +
                  "possible components. Note that, the stoichiometry of A " +
                  "is not described, nor is it stated that B is a required " +
                  "component. The use of hasComponent relationships is " +
                  "complementary to the use of functionally composed " +
                  "complexes and is intended to enable the assignment of " +
                  "components to complexes designated by names in external " +
                  "vocabularies. The assignment of components can " +
                  "potentially enable the reconciliation of equivalent " +
                  "complexes at knowledge assembly time.";
            break;
        case HAS_COMPONENTS:
            ret = "The hasComponents relationship is a special form which " +
                  "enables the assignment of multiple complex components " +
                  "in a single statement where the object of the statement " +
                  "is a set of abundance terms. A statement using " +
                  "hasComponents is exactly equivalent to multiple " +
                  "hasComponent statements. A term may not appear in both " +
                  "the subject and object of the same hasComponents " +
                  "statement. For the abundance terms A, B, C and D, A " +
                  "hasComponents B, C, D indicates that A has components B, " +
                  "C and D.";
            break;
        case HAS_MEMBER:
            ret = "For term abundances A and B, A hasMember B designates B " +
                  "as a member class of A. A member class is a " +
                  "distinguished sub-class. A is defined as a group by all " +
                  "of the members assigned to it. The member classes may or " +
                  "may not be overlapping and may or may not entirely cover " +
                  "all instances of A. A term may not appear in both the " +
                  "subject and object of the same hasMember statement.";
            break;
        case HAS_MEMBERS:
            ret = "The hasMembers relationship is a special form which " +
                  "enables the assignment of multiple member classes in a " +
                  "single statement where the object of the statement is a " +
                  "set of abundance terms. A statement using hasMembers is " +
                  "exactly equivalent to multiple hasMember statements. A " +
                  "term may not appear in both the subject and object of a " +
                  "of the same hasMembers statement. For the abundance " +
                  "terms A, B, C and D, A hasMembers B, C, D indicates " +
                  "that A is defined by its member abundance classes B, " +
                  "C and D.";
            break;
        case INCREASES:
            ret = "For terms A and B, A increases B or A -> B indicates " +
                 "that increases in A have been observed to cause increases " +
                 "in B. A increases B also represents cases where decreases " +
                 "in A have been observed to cause decreases in B, for " +
                 "example, in recording the results of gene deletion or " +
                 "other inhibition experiments. A is a BEL Term and B is " +
                 "either a BEL Term or a BEL Statement. The relationship " +
                 "does not indicate that the changes in A are either " +
                 "necessary for changes in B, nor does it indicate that " +
                 "changes in A are sufficient to cause changes in B.";
            break;
        case IS_A:
            ret = "For terms A and B, A isA B indicates that A is a subset " +
                  "of B. All terms in BEL represent classes, but given that " +
                  "classes implicitly have instances, one can also " +
                  "interpret A isA B to mean that any instance of A must " +
                  "also be an instance of B. This relationship can be used " +
                  "to represent GO and MeSH hierarchies: " +
                  "pathology(MESH:Psoriasis) isA " +
                  "pathology(MESH:\"Skin Diseases\").";
            break;
        case NEGATIVE_CORRELATION:
            ret = "For terms A and B, A negativeCorrelation B indicates " +
                  "that changes in A and B have been observed to be " +
                  "negatively correlated. The order of the subject and " +
                  "object does not affect the interpretation of the " +
                  "statement, thus B negativeCorrelation A is equivalent " +
                  "to A negativeCorrelation B.";
            break;
        case ORTHOLOGOUS:
            ret = "For geneAbundance terms A and B, A orthologousTo B " +
                  "indicates that A and B represent abundances of genes in " +
                  "different species which are sequence similar and which " +
                  "are therefore presumed to share a common ancestral gene.";
            break;
        case POSITIVE_CORRELATION:
            ret = "For terms A and B, A positiveCorrelation B indicates " +
                  "that changes in A and B have been observed to be " +
                  "positively correlated, thus B positiveCorrelation A is " +
                  "equivalent to A positiveCorrelation B.";
            break;
        case PROGNOSTIC_BIOMARKER_FOR:
            ret = "For term A and process term P, A prognosticBiomarkerFor " +
                  "P indicates that changes in or detection of A is used in " +
                  "some way to be a prognostic biomarker for the subsequent " +
                  "development of pathology or biological process P.";
            break;
        case RATE_LIMITING_STEP_OF:
            ret = "For process, activity, or transformation term A and " +
                  "process term B, A rateLimitingStepOf B indicates A " +
                  "subProcessOf B and A -> B.";
            break;
        case SUB_PROCESS_OF:
            ret = "For process, activity, or transformation term A and " +
                  "process term B, A subProcessOf B indicates that " +
                  "instances of process B, by default, include one or more " +
                  "instances of A in their composition.";
            break;
        case TRANSCRIBED_TO:
            ret = "For rnaAbundance term R and geneAbundance term G, G " +
                  "transcribedTo R or G :> R indicates that members of R " +
                  "are produced by the transcription of members of G. For " +
                  "example: g(HGNC:AKT1) :> r(HGNC:AKT1) indicates that the " +
                  "human AKT1 RNA is transcribed from the human AKT1 gene.";
            break;
        case TRANSLATED_TO:
            ret = "For rnaAbundance term R and proteinAbundance term P, R " +
                  "translatedTo P or R >> P indicates that members of P are " +
                  "produced by the translation of members of R. For " +
                  "example: r(HGNC:AKT1) >> p(HGNC:AKT1) indicates that " +
                  "AKT1 protein is produced by translation of AKT1 RNA.";
            break;
        }
        return ret;
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
