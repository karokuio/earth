#!/bin/sh

SLEEP_TIME=60
# The blackbox needs the database to be up and running to boot correctly
# so we sleep to wait for that connetion to be enabled
echo "Starting blackbox..."
echo " -- sleeping for $SLEEP_TIME seconds -- "
sleep $SLEEP_TIME
echo " -- starting -- "
java -Djava.security.egd=file:/dev/./urandom -jar earth.jar
