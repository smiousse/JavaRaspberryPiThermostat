#!/bin/sh
echo -- Starting JARPIT  ----------------
cd /home/pi/jarpit/
nohup java  -Djarpit.home.path=/home/pi/jarpit/ -jar jarpit-1.0-SNAPSHOT-jar-with-dependencies.jar </dev/null >jarpit.log 2>&1 &