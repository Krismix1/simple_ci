#!/usr/bin/env bash
set -eu
echo $1 > /dev/null # dirty hack of checking that parameter was set
mvn clean package && java -jar target/*-jar-with-dependencies.jar $1
