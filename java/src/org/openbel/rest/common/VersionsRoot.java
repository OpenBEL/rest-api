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

import static org.openbel.rest.common.Objects.*;
import org.openbel.rest.Path;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;

/**
 * Provides API version information.
 */
@Path("/api/versions")
public class VersionsRoot extends ServerResource {
	private final static Versions VERSIONS;
	static {
		VERSIONS = new Versions();
	}

    @Get("json")
    public Representation _get() {
    	return VERSIONS.json();
    }

}
