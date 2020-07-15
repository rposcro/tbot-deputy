#!/bin/bash
# This script runs ThingBot Deputy.

###### Main

if [ $(id -u) != "0" ]; then
        echo "You need to run it as root!"
        exit 1
fi

CONFIGDIR=/etc/tbot-deputy
BINDIR=/srv/tbot-deputy

LOGCONFIGFILE=${CONFIGDIR}/logback.xml
JARFILE=${BINDIR}/tbot-deputy.jar

echo "java -Dspring.config.location=${CONFIGDIR}/ -Dlogging.config=${LOGCONFIGFILE} -jar ${JARFILE}"
exec java -Dspring.config.location=${CONFIGDIR}/ -Dlogging.config=${LOGCONFIGFILE} -jar ${JARFILE}
