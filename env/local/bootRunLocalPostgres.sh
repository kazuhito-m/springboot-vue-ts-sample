#!/usr/bin/env bash

SCRIPT_DIR=$(cd $(dirname $(readlink -f $0 || echo $0));pwd -P)
cd ${SCRIPT_DIR}/../..

args="--environment.config.path=${SCRIPT_DIR}/config-local-postgres.properties"
args="${args} --maindb.flyway.locations=classpath:db/migration/maindb,filesystem:${SCRIPT_DIR}/testdata/maindb"

./gradlew clean bootRun --args=${args}