DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

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
export _ENV_SRC="${_ENV_SRC:="$_ENV_TOPDIR/src"}"
export _ENV_TEST="${_ENV_TEST:="$_ENV_TEST/test"}"
export _ENV_DEPS="${_ENV_DEPS:="$_ENV_TOPDIR/deps"}"

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

if [ -r "${_ENV_OVERRIDE}" ]; then
    source ${_ENV_OVERRIDE} || exit 1
fi

