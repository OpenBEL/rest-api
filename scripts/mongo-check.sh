#!/usr/bin/env bash
#
# Checks MongoDB is setup correctly.
#
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"/../
. "$DIR"/env.sh || exit 1
cd "${_ENV_TOOLS}/generic-python" || exit 1

ENV="${_ENV_TOOLS}/generic-python/env"
PROMPT="\n(\e[38;5;196mgeneric-python\e[0;0;0m)"
BS_ARGS="--quiet --python=python3 --prompt=${PROMPT} ${ENV}"

NEW_ENV=0
echo "Python virtual environment: ${ENV}"
if [ ! -d "${ENV}" -o $_ENV_REQ_DEPS -nt "${ENV}" ]; then
    echo "Python virtual environment out-of-date - it will be created."
    rm -fr "${ENV}"
    EXTRA_SEARCH_DIR="--extra-search-dir=$_ENV_VENDOR_HOME"
    VIRTENV_ARGS="--never-download --distribute ${ENV} $EXTRA_SEARCH_DIR"
    python3.3 $_ENV_VENDOR_HOME/virtualenv/virtualenv.py $VIRTENV_ARGS || exit 1
    NEW_ENV=1
else
    echo "Python virtual environment is sane."
fi

. "${ENV}"/bin/activate
if [ $NEW_ENV -eq 1 ]; then
    PKG_DIR="$_ENV_TOOLS/generic-python/packages"
    PIP_OPTS="--quiet --no-index --find-links=$PKG_DIR"
    pip install $PIP_OPTS -r $_ENV_REQ_DEPS || exit 1
fi

. ${ENV}/bin/activate
cd "${_ENV_PYTHON}" || exit 1
exec python3 src $@
deactivate

