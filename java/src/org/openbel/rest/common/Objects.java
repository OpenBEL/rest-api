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

import static java.lang.System.*;
import static org.openbel.rest.Util.*;
import java.lang.annotation.*;
import java.util.*;
import org.restlet.Request;
import org.restlet.representation.Representation;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.map.annotate.*;
import org.openbel.rest.Path;

/*
 * Objects used by common resources.
 */
public class Objects {

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

        public void addDocumentation(String page) {
            page = "/docs/api/" + page + ".html";
            addLink("describedby", page);
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
                link.href = declaredPath(cls);
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
        public List<Annotation> annotations;
        {
            annotations = new ArrayList<>();
            put("annotations", annotations);
        }
        public void addAnnotation(Annotation a) { annotations.add(a); }
    }

    public static class NSCompletion extends Base {

    }

    public static class NSKeywordCompletion extends Base {
        public List<String> values;
        {
            addDocumentation("namespace-keyword-completion");
            values = new ArrayList<>();
            put("values", values);
        }
        public void addValue(String s) { values.add(s); }
    }

    public static class NSValueCompletion extends Base {
        public List<String> values;
        {
            addDocumentation("namespace-value-completion");
            values = new ArrayList<>();
            put("values", values);
        }
        public void addValue(String s) { values.add(s); }
    }

    public static class AOCompletion extends Base {

    }

    public static class AOKeywordCompletion extends Base {
        public List<String> values;
        {
            addDocumentation("annotation-keyword-completion");
            values = new ArrayList<>();
            put("values", values);
        }
        public void addValue(String s) { values.add(s); }
    }

    public static class AOValueCompletion extends Base {
        public List<String> values;
        {
            addDocumentation("annotation-value-completion");
            values = new ArrayList<>();
            put("values", values);
        }
        public void addValue(String s) { values.add(s); }
    }

    @Path("/api/v1/namespaces")
    public static class Namespaces extends Base {
        public List<Namespace> namespaces;
        {
            namespaces = new ArrayList<>();
            put("namespaces", namespaces);
        }
        public void addNamespace(Namespace n) { namespaces.add(n); }
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
        public List<Function> functions;
        {
            functions = new ArrayList<>();
            put("functions", functions);
            addLink("related", Signatures.class);
        }
        public void addFunction(Function f) { functions.add(f); }
    }

    public static class Function extends Base {
        public String name; // required
        public String abbreviation; // not required
        public List<Signature> signatures;
        public Function(String name, String abbreviation) {
            this.name = name;
            this.abbreviation = abbreviation;
            put("name", name);
            if (abbreviation != null) put("abbreviation", abbreviation);
        }
        public void addSignature(Signature s) {
            if (signatures == null) {
                signatures = new ArrayList<>();
                put("signatures", signatures);
            }
            signatures.add(s);
        }
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
        public List<Relationship> relationships;
        {
            relationships = new ArrayList<>();
            put("relationships", relationships);
        }
        public void addRelationship(Relationship r) { relationships.add(r); }
    }

    public static class Relationship extends Base {
        public String name; // required
        public String abbreviation; // not required
        public Relationship(String name, String abbreviation) {
            this.name = name;
            this.abbreviation = abbreviation;
            put("name", name);
            if (abbreviation != null) put("abbreviation", abbreviation);
        }
    }

    public static class Signature extends Base {
        public String value; // required
        public String numArgs; // required
        public String returnType; // required
        public Signature(String value, String numArgs, String returnType) {
            this.value = value;
            this.numArgs = numArgs;
            this.returnType = returnType;
            put("value", value);
            put("number_of_arguments", numArgs);
            put("return_type", returnType);
        }

    }

    public static class Namespace extends Base {
        public String description; // not required
        public String name; // required
        public String keyword; // required
        public Namespace(String name, String keyword, String description) {
            this.name = name;
            this.keyword = keyword;
            this.description = description;
            put("name", name);
            put("keyword", keyword);
            if (description != null) put("description", description);
        }
    }

    public static class Annotation extends Base {
        public String description; // not required
        public String usage; // not required
        public String name; // required
        public String keyword; // required
        public String type; // required
        public Annotation(String name, String keyword, String type) {
            this.name = name;
            this.keyword = keyword;
            this.type = type;
            put("name", name);
            put("keyword", keyword);
            put("type", type);
        }
        public void setDescription(String s) {
            put("description", s);
            this.description = s;
        }
        public void setUsage(String s) {
            put("usage", s);
            this.usage = s;
        }
    }

    public static class Validation extends Base {
        public boolean valid;
        public Validation(boolean valid) {
            this.valid = valid;
            put("valid", valid);
        }
    }

    public static class ScriptValidation extends Validation {
        public ScriptValidation(boolean valid) {
            super(valid);
            put("syntax_errors", new ArrayList<String>());
            put("syntax_warnings", new ArrayList<String>());
        }
        public void addSyntaxError(String error) {
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) get("syntax_errors");
            list.add(error);
        }
        public void addSyntaxWarning(String warning) {
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) get("syntax_warnings");
            list.add(warning);
        }
    }

    public static class Validations extends Base {
        public void addTermValidation(Validation v) {
            put("term", v);
        }
        public void addStatementValidation(Validation v) {
            put("statement", v);
        }
        public void addScriptValidation(ScriptValidation v) {
            put("script", v);
        }
    }

}
