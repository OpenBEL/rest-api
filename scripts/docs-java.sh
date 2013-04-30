#!/usr/bin/env bash
#
# Generates Java API documentation.
#
# Executes from top-level dir
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "${DIR}"/../ || exit 1
. env.sh || exit 1

echo "
##########################
# JAVA API DOCUMENTATION #
##########################
"

echo "Using default output directory '${_ENV_JAVA_DOCS}'."

CLASSPATH=$(find "${_ENV_DEPS}" -name *.jar -print0 | xargs -0 | tr ' ' ':')
JAVA_API_LINK="http://docs.oracle.com/javase/7/docs/api/"

if [ ! -z "$JAVA_HOME" ]; then
    JAVADOC="$JAVA_HOME"/bin/javadoc
else
    JAVADOC=javadoc
fi
echo "Using javadoc command '${JAVADOC}'."

mkdir -p "${_ENV_JAVA_DOCS}"
RSLT=$($JAVADOC -source 1.7 \
                -package \
                -sourcepath "${_ENV_SRC}" \
                -subpackages org \
                -classpath ${CLASSPATH} \
                -link ${JAVA_API_LINK} \
                -d "${_ENV_JAVA_DOCS}" \
                -encoding "UTF-8" \
                -quiet)

if [ ! -z "${RSLT}" ]; then
    echo "${RSLT}"
    echo "Errors in Java API docs!" >&2
    exit 1
else
    echo "Generating Java API documentation produced no warnings."
    echo "You did a good job."
fi
exit 0

