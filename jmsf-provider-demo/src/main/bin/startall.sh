#!/usr/bin/env bash

set -xe

# bin目录绝对路径
BIN_PATH=$(cd `dirname $0`; pwd)

(. ${BIN_PATH}/sgm.sh && ${BIN_PATH}/startup.sh)
