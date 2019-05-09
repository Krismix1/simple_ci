#!/usr/bin/env bash

set -ue

source helper.sh

SOURCE_PATH=$1
OUTPUT_PATH=$2

if [[ ! -d ${SOURCE_PATH} ]]; then
    echo "Source directory not found" 1>&2
    exit 1
fi

if [[ -d ${OUTPUT_PATH} ]]; then
    echo "Repository already exists"
    exit 0
fi

check_output "Couldn't clone repository" git clone ${SOURCE_PATH} ${OUTPUT_PATH}