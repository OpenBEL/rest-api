#!/usr/bin/env bash
#
# Generates REST API documentation.
#
# Executes from top-level dir
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "${DIR}"/../ || exit 1
. env.sh || exit 1

echo "
##########################
# REST API DOCUMENTATION #
##########################
"

cd "${_ENV_REST_DOCS}" || exit 1
ENV="${_ENV_REST_DOCS}"/docs-env
echo "Python virtual environment: ${ENV}"
if [ ! -d "${ENV}" -o $_ENV_REQ_DEPS -nt "${ENV}" ]; then
    echo "Python virtual environment out-of-date - it will be created."
    rm -fr "${ENV}"
    EXTRA_SEARCH_DIR="--extra-search-dir=$_ENV_VENDOR_HOME"
    VIRTENV_ARGS="--never-download --distribute ${ENV} $EXTRA_SEARCH_DIR"
    python2 $_ENV_VENDOR_HOME/virtualenv/virtualenv.py $VIRTENV_ARGS || exit 1
fi

echo harro
. "${ENV}"/bin/activate || exit 1
PKG_DIR="$_ENV_REST_DOCS/packages"
PIP_OPTS="--quiet --no-index --find-links=$PKG_DIR"
pip install $PIP_OPTS -r $_ENV_REQ_DEPS || exit 1
make clean html
if [ $? -ne 0 ]; then
    echo "Errors in REST API docs!" >&2
    exit 1
else
    echo "Generating REST API documentation produced no warnings."
    echo "You did a good job. No, wait -- a FANTASTIC job."
fi
deactivate
exit 0

