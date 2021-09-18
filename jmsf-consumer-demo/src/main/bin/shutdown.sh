#! /bin/bash

#======================================================================
# 项目停服shell脚本
# 通过项目名称查找到PID
# 然后kill -15 pid
#======================================================================

# 项目名称
APPLICATION="@project.name@"

# 项目启动jar包名称
APPLICATION_JAR="@build.finalName@.jar"
SYSOS=$(uname -s)
if [ "$SYSOS" == "Darwin" ];then
	  PID=$(ps -ef | grep "${APPLICATION_JAR}" | awk '{ print $2 }')
	  echo "Current OS: $SYSOS, pid: $PID"
elif [ "$SYSOS" == "Linux" ];then
	  PID=$(ps -ef | grep "${APPLICATION_JAR}" | grep -v grep | awk '{ print $2 }')
	  echo "Current OS: $SYSOS, pid: $PID"
else
	  echo "Other OS: $SYSOS"
fi
if [[ -z "$PID" ]]
then
    echo ${APPLICATION} is already stopped
else
    echo kill ${PID}
    kill -15 ${PID}
    echo ${APPLICATION} stopped successfully
fi
