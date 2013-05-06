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

import org.openbel.framework.common.cfg.SystemConfiguration;
import org.restlet.*;
import sun.misc.*;
import java.util.*;

import static java.lang.System.*;
import static java.lang.Runtime.*;
import static java.lang.Thread.*;
import static org.restlet.data.Protocol.*;
import static java.lang.Integer.*;
import static org.openbel.framework.common.cfg.SystemConfiguration.*;

class main extends Component {
    static int port;
    static String cache;
    static String work;
    static String dburl;
    static String residx;
    static String mongoHost;
    static String mongoDB;
    static APIApplication apiapp;

    public main() {
        getServers().add(HTTP, port);
        getDefaultHost().attachDefault(apiapp);
    }

    public void init() {
        out.print("Bootstrapping MongoDB... ");
        out.flush();
        // TODO here
        out.println("okay");
    }

    public static void main(String... args) {
        String value = getenv("_ENV_PORT");
        boolean configured = true;
        if (value == null) {
            err.println("no _ENV_PORT is set");
            configured = false;
        } else port = parseInt(value);
        cache = getenv("_ENV_BEL_CACHE");
        if (cache == null) {
            err.println("no _ENV_BEL_CACHE is set");
            configured = false;
        }
        work = getenv("_ENV_BEL_WORK");
        if (work == null) {
            err.println("no _ENV_BEL_WORK is set");
            configured = false;
        }
        dburl = getenv("_ENV_BEL_DBURL");
        if (dburl == null) {
            err.println("no _ENV_BEL_DBURL is set");
            configured = false;
        }
        residx = getenv("_ENV_BEL_RESIDX");
        if (residx == null) {
            err.println("no _ENV_BEL_RESIDX is set");
            configured = false;
        }
        mongoHost = getenv("_ENV_MONGO_HOST");
        if (mongoHost == null) {
            err.println("no _ENV_MONGO_HOST");
            configured = false;
        }
        mongoDB = getenv("_ENV_MONGO_DB");
        if (mongoDB == null) {
            err.println("no _ENV_MONGO_DB");
            configured = false;
        }
        if (!configured) exit(1);
        out.println("PORT: " + port);
        out.println("CACHE: " + cache);
        out.println("WORK: " + work);
        out.println("DBURL: " + dburl);
        out.println("RESOURCE INDEX: " + residx);
        out.println("MONGODB HOST: " + mongoHost);
        out.println("MONGODB DB: " + mongoDB);
        out.println();

        out.print("Bootstrapping the framework... ");
        // Bootstrap the framework
        Map<String, String> map = new HashMap<>();
        map.put(FRAMEWORK_CACHE_DIRECTORY_DESC, cache);
        map.put(FRAMEWORK_WORKING_AREA_DESC, work);
        map.put(KAMSTORE_URL_DESC, dburl);
        map.put(RESOURCE_INDEX_URL_DESC, residx);
        SystemConfiguration syscfg = createSystemConfiguration(map);
        out.println("okay");

        apiapp = new APIApplication();
        final main main = new main();
        try {
            main.init();
            main.start();
        } catch (Exception e) {
            e.printStackTrace();
            exit(1);
        }
        Signal sigint = new Signal("INT");
        Signal.handle(sigint, new SignalHandler() {
            public void handle(Signal sig) {
                try {
                    main.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                    exit(1);
                }
            }
        });
    }

}
