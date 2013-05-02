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

import static java.lang.System.*;
import static org.openbel.rest.Util.*;
import java.lang.annotation.*;
import java.util.*;
import org.restlet.Request;
import org.restlet.representation.Representation;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.map.annotate.*;

/**
 * Objects used by common resources.
 */
public class Objects {

    /**
     * Type annotation used to indicate the path to a resource.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Path {
        String value();
    }

    public static class Link {
        public String rel;
        public String href;
        public String getRel() { return rel; }
        public String getHref() { return asURL(href); }
    }

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    public static class Base extends HashMap<String, Object> {

        {
            Class<?> cls = this.getClass();
            if (cls.isAnnotationPresent(Path.class)) {
                addLink("self", cls);
            }
        }

        public void addLink(String rel, Object href) {
            if (!this.containsKey("_links")) {
                put("_links", new ArrayList<Link>());
            }
            Link link = new Link();
            link.rel = rel;
            if (href instanceof String)
                link.href = (String) href;
            else if (href instanceof Class) {
                Class<?> cls = (Class) href;
                if (cls.isAnnotationPresent(Path.class)) {
                    Path p = cls.getAnnotation(Path.class);
                    link.href = p.value();
                }
            }
            @SuppressWarnings("unchecked")
            List<Link> links = (List<Link>) this.get("_links");
            links.add(link);
        }

        public Representation json() {
            return new JacksonRepresentation<>(this);
        }

        public String string(String k) { return (String) get(k); }
            @SuppressWarnings("unchecked")
        public <T> List<T> list(String k) { return (List<T>) get(k); }
    }

    @Path("/api")
    public static class API extends Base {
        public String release;
        {
            release = getenv("_ENV_VERSION");
            put("release", release);
            addLink("subsection", Versions.class);
            addLink("latest-version", V1.class);
        }
    }

    @Path("/api/versions")
    public static class Versions extends Base {
        public List<Version> versions;
        {
            versions = new ArrayList<>();
            put("versions", versions);
            versions.add(new V1());
            addLink("current", V1.class);
        }
    }

    public static class Version extends Base {
        public int version;
        public Version(int version) {
            put("version", version);
            this.version = version;
        }
    }

    @Path("/api/v1")
    public static class V1 extends Version {
        public V1() {
            super(1);
            addLink("describedby", V1.class);
            addLink("up", Versions.class);
            addLink("subsection", Parameters.class);
            addLink("subsection", Annotations.class);
            addLink("subsection", Namespaces.class);
            addLink("subsection", Statements.class);
            addLink("subsection", Terms.class);
            addLink("subsection", Lang.class);
        }
    }

    @Path("/api/v1/lang")
    public static class Lang extends Base {
        private static final String LANG_URL;
        static {
            LANG_URL = "http://wiki.openbel.org/display/BLD";
        }
        {
            addLink("describedby", LANG_URL);
            addLink("subsection", Functions.class);
            addLink("subsection", Relationships.class);
        }
    }

    @Path("/api/v1/annotations")
    public static class Annotations extends Base {
        {
        }
    }

    @Path("/api/v1/namespaces")
    public static class Namespaces extends Base {
        {

        }
    }

    @Path("/api/v1/statements")
    public static class Statements extends Base {
        {

        }
    }

    @Path("/api/v1/terms")
    public static class Terms extends Base {
        {

        }
    }

    @Path("/api/v1/parameters")
    public static class Parameters extends Base {
        {

        }
    }

    public static class Parameter extends Base {
        {

        }
    }

    @Path("/api/v1/lang/functions")
    public static class Functions extends Base {
        {
            addLink("related", Signatures.class);
        }
    }

    public static class Function extends Base {
        public String name;
        public String abbreviation;
        public List<String> signatures;
        public Function(String name, String abbreviation) {
            this.name = name;
            this.abbreviation = abbreviation;
            put("name", name);
            if (abbreviation != null)
                put("abbreviation", abbreviation);
            signatures = new ArrayList<>();
            put("signatures", signatures);
        }
        public void addSignature(String s) { signatures.add(s); }
    }

    @Path("/api/v1/lang/functions/signatures")
    public static class Signatures extends Base {
        public List<Function> functions;
        {
            functions = new ArrayList<>();
            put("functions", functions);
        }
        public void addFunction(Function f) { functions.add(f); }
    }

    @Path("/api/v1/lang/relationships")
    public static class Relationships extends Base {
        {
            addLink("related", Descriptions.class);
        }
    }

    public static class Relationship extends Base {
        {

        }
    }

    @Path("/api/v1/lang/relationships/descriptions")
    public static class Descriptions extends Base {
        {

        }
    }

    public static class Description extends Base {
        {

        }
    }

}
