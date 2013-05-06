#!/usr/bin/env bash
#
# Runs the REST API.
#
# Executes from top-level dir
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"/../
. "$DIR"/env.sh || exit 1
cd "$_ENV_JAVA" || exit 1

buildr run $@

