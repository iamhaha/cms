#!/usr/bin/env bash

APPLICATION=cms-app
ps ux | grep ${APPLICATION} | grep -v grep | grep -v stop.sh | awk '{print $2}' | xargs kill -9

if [ $? -ne 0 ] ; then
  echo "kill ${APPLICATION} error"
  exit -1
fi
echo "kill ${APPLICATION} success"