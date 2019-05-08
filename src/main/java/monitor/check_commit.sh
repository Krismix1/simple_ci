#!/usr/bin/env bash

set -ue

source helper.sh

rm -f .commit_hash

check_output "repo dir not found" pushd $1 1> /dev/null
check_output "can't reset git repo" git reset --hard HEAD

HASH=$(check_output "can't call 'git log' on repo" git log -n1 --format=%H)
if [[ $? != 0 ]]; then
    echo "can't call 'git log' on repo"
    exit 1
fi

check_output "can't pull from repo" git pull

NEW_HASH=$(check_output "can't call 'git log' on repo" git log -n1 --format=%H)
if [[ $? != 0 ]]; then
    echo "can't call 'git log' on repo"
    exit 1
fi

if [[ ${NEW_HASH} != ${HASH} ]]; then
    popd 1> /dev/null
    echo ${NEW_HASH} > .commit_hash
    exit 0
fi
exit 1