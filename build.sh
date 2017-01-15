#!/usr/bin/env bash
if [ $# -eq 0 ] ; then
  mvn -Dmaven.test.skip=true clean package -U
else
  if [ $1 = "-t" ] ; then
    mvn -Dspring.profiles.active=test clean package -U
  else
    echo "build.sh [-t]"
    exit -1
  fi
fi

if [ $? -ne 0 ] ; then
  echo "mvn package error"
  exit -1
fi

rm -rf output
mkdir output

mkdir -p output/bin
mkdir -p output/conf
cp cms-app/target/cms-app-*.jar output/bin/cms-app.jar
cp cms-app/deploy/*.sh output/bin/
cp cms-app/conf/* output/conf/