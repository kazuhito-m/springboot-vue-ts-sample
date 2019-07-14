#!/usr/bin/env bash

SCRIPT_DIR=$(cd $(dirname $(readlink -f $0 || echo $0));pwd -P)
cd ${SCRIPT_DIR}/../..

arg1="--environment.config.path=${SCRIPT_DIR}/config-local-postgres.properties"
arg2="--maindb.flyway.locations=classpath:db/migration/maindb,filesystem:${SCRIPT_DIR}/testdata/maindb"

./gradlew clean bootRun --args="${arg1} ${arg2}"