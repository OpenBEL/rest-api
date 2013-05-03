DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
export _ENV_VERSION=${_ENV_VERSION:-experimental}

### Special rules on syntax ###
#
# To add a variable that should default to itself or another variable:
#   e.g., set VAR to VAR if set otherwise PATH:
#     export VAR=${VAR:=$PATH}
#
# To add a variable that should default to itself or something hard-coded:
#   e.g., set VAR to VAR if set otherwise "FOO":
#     export VAR=${VAR:-FOO}

### PATHS ###
export _ENV_TOPDIR="${_ENV_TOPDIR:="$DIR"}"
export _ENV_SCRIPTS="${_ENV_SCRIPTS:="$_ENV_TOPDIR/scripts"}"
export _ENV_TOOLS="${_ENV_TOOLS:="$_ENV_TOPDIR/tools"}"
export _ENV_SRC="${_ENV_SRC:="$_ENV_TOPDIR/src"}"
export _ENV_TEST="${_ENV_TEST:="$_ENV_TEST/test"}"
export _ENV_DEPS="${_ENV_DEPS:="$_ENV_TOPDIR/deps"}"
export _ENV_DOCS="${_ENV_DOCS:="$_ENV_TOPDIR/docs"}"
export _ENV_REST_DOCS="${_ENV_REST_DOCS:="$_ENV_DOCS/rest"}"
export _ENV_JAVA_DOCS="${_ENV_JAVA_DOCS:="$_ENV_DOCS/java"}"
export _ENV_VENDOR_HOME="${_ENV_VENDOR_HOME:="$_ENV_TOOLS/vendor"}"

### SETTINGS ###
export _ENV_PORT=${_ENV_PORT:-60000}
export _ENV_OVERRIDE="${_ENV_OVERRIDE:="$_ENV_TOPDIR/env.sh.custom"}"
export _ENV_BEL_ROOT="${_ENV_BEL_ROOT:="$_ENV_TOPDIR/framework"}"
export _ENV_BEL_CACHE="${_ENV_BEL_CACHE:="$_ENV_BEL_ROOT/cache"}"
export _ENV_BEL_WORK="${_ENV_BEL_WORK:="$_ENV_BEL_ROOT/work"}"
DEFAULT_DBURL="jdbc:derby:${_ENV_BEL_ROOT}/db;create=true;"
export _ENV_BEL_DBURL="${_ENV_BEL_DBURL:="$DEFAULT_DBURL"}"
DEFAULT_RESIDX="http://resource.belframework.org/belframework/1.0/index.xml"
export _ENV_BEL_RESIDX="${_ENV_BEL_RESIDX:="$DEFAULT_RESIDX"}"
export _ENV_MONGO_DB=${_ENV_MONGO_DB:-restapi}
export _ENV_MONGO_HOST=${_ENV_MONGO_HOST:-localhost}

### MISCELLANEOUS ###
# Convention for required Python dependencies
export _ENV_REQ_DEPS=${_ENV_REQ_DEPS:-deps.req}
# Convention for optional Python dependencies
export _ENV_OPT_DEPS=${_ENV_OPT_DEPS:-deps.opt}

if [ -r "${_ENV_OVERRIDE}" ]; then
    source ${_ENV_OVERRIDE} || exit 1
fi

