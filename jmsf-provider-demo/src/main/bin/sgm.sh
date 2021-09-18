#!/usr/bin/env bash

SGM_APP_NAME=$APP_NAME
SGM_SERVER_URL="http://sgm.jdfin.local/local/agent/version?app=$SGM_APP_NAME"
echo "sgm-server url: $SGM_SERVER_URL"
SGM_BASE_DIR="/export/sgm-probe"
if [ ! -d "$SGM_BASE_DIR" ];then
    mkdir $SGM_BASE_DIR
fi
SGM_VERSION=`/usr/bin/curl -s --connect-timeout 3 $SGM_SERVER_URL`
echo "Response from sgm-server: $SGM_VERSION"
if [[ $SGM_VERSION == disable* ]];then
    echo "Monitor has been disabled by sgm-server"
else
    if [[ $SGM_VERSION == 3.* || $SGM_VERSION == 5.* || $SGM_VERSION == 7.* || $SGM_VERSION == 9.* ]];then
        SGM_PROBE_DIR="$SGM_BASE_DIR/sgm-probe-$SGM_VERSION"
        SGM_PROBE_TAR="$SGM_PROBE_DIR.tar.gz"
        #snapshot版本或未下载过
        if [[ $SGM_VERSION == *SNAPSHOT* || ! -d "$SGM_PROBE_DIR" ]];then
            SGM_BASE_DOWNLOAD="http://sgm-download.jdfin.local/wypkgs"
            SGM_PROBE_PACKAGE="$SGM_BASE_DOWNLOAD/sgm-probe-$SGM_VERSION.tar.gz"
            /usr/bin/wget --tries=3 --no-verbose -q --timeout=20 -O $SGM_PROBE_TAR $SGM_PROBE_PACKAGE && tar zxf $SGM_PROBE_TAR -C $SGM_BASE_DIR
            echo "Download SGM Probe from $SGM_PROBE_PACKAGE"
        fi
    else
        SGM_PROBE_DIR=`ls -td $SGM_BASE_DIR/sgm-probe*/ | head -1`
    fi
    echo "SGM PROBE DIR: $SGM_PROBE_DIR"
    #sgm探针外层目录
    SGM_DIR=$SGM_PROBE_DIR
    if [ -d "$SGM_DIR" ]; then
        SGM_AGENT=`ls $SGM_DIR | grep 'sgm-agent*' | grep '.jar'`
        if [ -n "$SGM_AGENT" ]; then
            SGM_AGENT="$SGM_DIR/$SGM_AGENT"
            SGM_OPTS="-javaagent:$SGM_AGENT -Xbootclasspath/a:$SGM_AGENT"
            if [ -n "$SGM_APP_NAME" ]; then
                SGM_OPTS="$SGM_OPTS -Dsgm.app.name=$SGM_APP_NAME"
            fi
            export SGM_OPTS
            echo "Using SGM_OPTS:       $SGM_OPTS"
        fi
    fi
fi
