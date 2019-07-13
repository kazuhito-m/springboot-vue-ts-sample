#!/usr/bin/env bash

SCRIPT_DIR=$(cd $(dirname $(readlink -f $0 || echo $0));pwd -P)
cd ${SCRIPT_DIR}/../..

./gradlew clean bootRun --args="--environment.config.path=${SCRIPT_DIR}/config-local-postgres.properties"
