#!/usr/bin/env bash
APPLICATION=cms-app
SPRING_CONFIG_FILE=../conf/application.yml
LOG_FILE_DEBUG=../log/debug/cms-debug.log
LOG_FILE_ACCESS=../log/access/cms-access.log
MAX_MEMORY=5120M
MAX_PERM_MEMORY=512M

nohup java -Dspring.config.location=${SPRING_CONFIG_FILE} -Dfile.encoding=UTF-8 \
-Dlogging.file.debug=${LOG_FILE_DEBUG} -Dlogging.file.access=${LOG_FILE_ACCESS} \
-Xmx${MAX_MEMORY} -XX:MaxPermSize=${MAX_PERM_MEMORY} -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps \
-Xloggc:gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=20M -jar ${APPLICATION}.jar > /dev/null 2>&1 &

if [ $? -ne 0 ] ; then
  echo "start ${APPLICATION} error"
  exit -1
fi
echo "start ${APPLICATION} success"