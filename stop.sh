#!/bin/sh
echo -- Stopping JARPIT  ----------------
kill $(ps aux | grep '[j]arpit' | awk '{print $2}')