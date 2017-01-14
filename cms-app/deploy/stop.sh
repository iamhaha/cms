#!/usr/bin/env bash

ps ux | grep "cms-app" | grep -v grep | grep -v stop.sh | cut -c 9-15 | xargs kill -s 9

for var in $@
do
    ps ux | grep "$var" | grep -v grep | grep -v stop.sh | cut -c 9-15 | xargs kill -s 9
done

echo "kill $@ done"