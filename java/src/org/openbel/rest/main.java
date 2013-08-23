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
package org.openbel.rest;

import org.openbel.framework.common.cfg.SystemConfiguration;
import org.restlet.*;
import sun.misc.*;
import java.util.*;
import com.mongodb.*;
import java.net.UnknownHostException;
import org.jongo.*;

import static java.lang.System.*;
import static java.lang.Runtime.*;
import static java.lang.Thread.*;
import static org.restlet.data.Protocol.*;
import static java.lang.Integer.*;
import static org.openbel.framework.common.cfg.SystemConfiguration.*;

public class main extends Component {
    public static String $top;
    public static int $port;
    public static String $cache;
    public static String $work;
    public static String $dburl;
    public static String $residx;
    public static String $mongoHost;
    public static String $mongoDB;
    public static DB $db;
    public static APIApplication $apiapp;
    public static MongoCollection $namespaces;
    public static MongoCollection $nsvalues;
    public static MongoCollection $annotations;
    public static MongoCollection $annovalues;

    main() {
        getServers().add(HTTP, $port);
        getDefaultHost().attachDefault($apiapp);
    }

    public void init() {
        out.print("Bootstrapping MongoDB... ");
        out.flush();
        try {
            $db = new MongoClient($mongoHost).getDB($mongoDB);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            exit(1);
        }
        Jongo jongo = new Jongo($db);
        $nsvalues = jongo.getCollection("nsvalues");
        $namespaces = jongo.getCollection("namespaces");
        $annovalues = jongo.getCollection("annovalues");
        $annotations = jongo.getCollection("annotations");
        out.println("okay");
    }

    public static void main(String... args) {
        String value = getenv("_ENV_PORT");
        boolean configured = true;
        $top = getenv("_ENV_TOPDIR");
        if ($top == null) {
            err.println("no _ENV_TOPDIR");
            configured = false;
        }
        if (value == null) {
            err.println("no _ENV_PORT is set");
            configured = false;
        } else $port = parseInt(value);
        $cache = getenv("_ENV_BEL_CACHE");
        if ($cache == null) {
            err.println("no _ENV_BEL_CACHE is set");
            configured = false;
        }
        $work = getenv("_ENV_BEL_WORK");
        if ($work == null) {
            err.println("no _ENV_BEL_WORK is set");
            configured = false;
        }
        $dburl = getenv("_ENV_BEL_DBURL");
        if ($dburl == null) {
            err.println("no _ENV_BEL_DBURL is set");
            configured = false;
        }
        $residx = getenv("_ENV_BEL_RESIDX");
        if ($residx == null) {
            err.println("no _ENV_BEL_RESIDX is set");
            configured = false;
        }
        $mongoHost = getenv("_ENV_MONGO_HOST");
        if ($mongoHost == null) {
            err.println("no _ENV_MONGO_HOST");
            configured = false;
        }
        $mongoDB = getenv("_ENV_MONGO_DB");
        if ($mongoDB == null) {
            err.println("no _ENV_MONGO_DB");
            configured = false;
        }
        if (!configured) exit(1);
        out.println("PORT: " + $port);
        out.println("CACHE: " + $cache);
        out.println("WORK: " + $work);
        out.println("DBURL: " + $dburl);
        out.println("RESOURCE INDEX: " + $residx);
        out.println("MONGODB HOST: " + $mongoHost);
        out.println("MONGODB DB: " + $mongoDB);
        out.println();

        out.print("Bootstrapping the framework... ");
        // Bootstrap the framework
        Map<String, String> map = new HashMap<>();
        map.put(FRAMEWORK_CACHE_DIRECTORY_DESC, $cache);
        map.put(FRAMEWORK_WORKING_AREA_DESC, $work);
        map.put(KAMSTORE_URL_DESC, $dburl);
        map.put(RESOURCE_INDEX_URL_DESC, $residx);
        SystemConfiguration syscfg = createSystemConfiguration(map);
        out.println("okay");

        $apiapp = new APIApplication();
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
