#!/usr/bin/env bash
#
# Cleans the current build.
#
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"/../
. "$DIR"/env.sh || exit 1

echo -en "Java... "
cd "$_ENV_JAVA" || exit 1
buildr test=no c --silent $@ >/dev/null
echo "okay"

echo -en "Clojure... "
cd "$_ENV_CLOJURE" || exit 1
lein clean
echo "okay"

