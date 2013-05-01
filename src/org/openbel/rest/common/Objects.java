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

import static org.openbel.rest.Util.*;
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

	public static class Link {
		public String rel;
		public String href;
		public String getRel() { return rel; }
		public String getHref() { return asURL(href); }
	}

	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public static class Base {
		public List<Link> _links;

		public void addLink(String rel, String href) {
			if (_links == null) _links = new ArrayList<>();
			Link link = new Link();
			link.rel = rel;
			link.href = href;
			_links.add(link);
		}

		public Representation json() {
			return new JacksonRepresentation<>(this);
		}
	}

	public static class Versions extends Base {
		private List<Version> versions;

		{
			versions = new ArrayList<>();
			versions.add(new V1());
			addLink("current", "/api/v1");
		}

		public List<Version> getVersions() { return versions; }

	}

	public static class Version extends Base {
		public int version;
		public int getVersion() { return version; }
	}

	public static class V1 extends Version {
		{
			version = 1;
			addLink("describedby", "/api/v1");
		}
	}

	public static class API extends Base {}
	public static class Annotations extends Base {}
	public static class Namespaces extends Base {}
	public static class Statements extends Base {}
	public static class Terms extends Base {}
	public static class Parameters extends Base {}
	public static class Parameter extends Base {}
	public static class Functions extends Base {}
	public static class Function extends Base {}
	public static class Signatures extends Base {}
	public static class Signature extends Base {}
	public static class Lang extends Base {}
	public static class Relationships extends Base {}
	public static class Relationship extends Base {}
	public static class Descriptions extends Base {}
	public static class Description extends Base {}

}
