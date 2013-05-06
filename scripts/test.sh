#!/usr/bin/env bash
#
# Run tests.
#
# Executes from top-level dir
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"/../
. "$DIR"/env.sh || exit 1
cd "$_ENV_TOPDIR" || exit 1
cd "$_ENV_JAVA" || exit 1

buildr test $@

