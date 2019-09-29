#!/usr/bin/env bash

if [ -z "$1" ]; then
    >&2 echo "Provide path for repository to be cloned."
    exit 1
fi

set -eu

REPO=$1

mvn clean package && java -jar target/*-jar-with-dependencies.jar "$REPO"
