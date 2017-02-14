#!/usr/bin/env bash

cd web
rm -fr dist
if [ $# -ne 0 ] ; then
  if [ $1 = "-i" ] ; then
    npm install
  else
    echo "web_build.sh [-i]"
    exit -1
  fi
fi
gulp

if [ $? -ne 0 ] ; then
  echo "web gulp error, please refer the logs"
  exit -1
fi

TARGET_DIR=../cms-app/src/main/resources/static/
rm -fr ${TARGET_DIR}
mkdir -p ${TARGET_DIR}
cp index.html ${TARGET_DIR}
cp -r dist ${TARGET_DIR}
cp -r tpl ${TARGET_DIR}
cp -r fonts ${TARGET_DIR}