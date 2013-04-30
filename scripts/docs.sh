#!/usr/bin/env bash
#
# Generates Java and REST API documentation.
#
# Executes from top-level dir
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "${DIR}"/../ || exit 1
. env.sh || exit 1

"$_ENV_SCRIPTS"/docs-java.sh || exit 1
"$_ENV_SCRIPTS"/docs-rest.sh || exit 1
exit 0

