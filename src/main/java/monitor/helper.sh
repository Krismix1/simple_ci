#!/usr/bin/env bash

# asserting

check_output() {
    local explanation=$1
    shift 1
    "$@"

    if [[ $? != 0 ]]; then
        echo ${explanation} 1>&2
        exit 1
    fi
}