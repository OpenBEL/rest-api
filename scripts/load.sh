#!/usr/bin/env bash
#
# Loads BEL data into MongoDB.
#
# Executes from clojure dir
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"/../
. "$DIR"/env.sh || exit 1
cd "$_ENV_CLOJURE" || exit 1

lein run

