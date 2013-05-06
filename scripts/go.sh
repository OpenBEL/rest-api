#!/usr/bin/env bash
#
# Simple menu to the REST API scripts.
#
# Executes from top-level REST API dir
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"/../
cd "${DIR}" || exit 1
. "$DIR"/env.sh || exit 1

# To add an additional item:
# 1. Add a function using examples below
# 2. Add an entry to the CHOICES array at the bottom

GO=$'\e[38;5;109m'
HIGHLIGHT=$'\e[38;5;226m'
WHITE=$'\e[38;5;255m'
GREY=$'\e[38;5;243m'
NONE=$'\e[0m'
PS3="
(${HIGHLIGHT}rest-api${NONE}) ${GO}go${WHITE}:${NONE} "

function script {
    echo -e "(\033[38;5;76m${1}\033[0;0;0m)"
    "${_ENV_SCRIPTS}"/$@
    return $?
}

function _1 {
    echo "Cleaning..."
    echo
    script "clean.sh" || return $?
}

function _2 {
    echo "Building..."
    echo
    script "build.sh" || return $?
}

function _3 {
    echo "Testing..."
    echo
    script "test.sh" || return $?
}

function _4 {
    echo "Running..."
    echo
    script "run.sh" || return $?
}

function _5 {
    echo "Documenting..."
    echo
    script "docs.sh" || return $?
}

function _6 {
    echo "Loading..."
    echo
    script "load.sh" || return $?
}

function _7 {
    echo "Check..."
    echo
    script "mongo-check.sh" || return $?
}

function _8 {
    echo "Watching "$_ENV_JAVA_SRC" for changes... "
    echo
    while true; do
        DIRS=$(find "$_ENV_JAVA_SRC" -type d -printf "%p ") || return $?
        python3 "$_ENV_SCRIPTS"/gate ${DIRS} || return $?
        script "build.sh"
    done
}

# Each menu option should stand alone on its own
# line and should be distinct from other entries.
CHOICES=(
         "clean" \
         "build" \
         "test" \
         "run" \
         "docs" \
         "load" \
         "check" \
         "build loop" \
         )

if [ $# -gt 0 ]; then
    echo
    echo "${GREY}Processing arguments before entering the shell.${NONE}"
    echo "${GREY}(${NONE}$@${GREY})${NONE}"
    echo
    for x in $@; do _$x || break; done
fi
echo
echo "${GREY}Entering go shell, [CTRL-C] to exit.${NONE}"
echo

select CHOICE in "${CHOICES[@]}"; do
    case $CHOICE in
    *) for x in $REPLY; do _$x || break; done;;
    esac
done
