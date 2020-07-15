#!/bin/sh
### BEGIN INIT INFO
# Provides:             tbot-deputy
# Required-Start:       $local_fs $network
# Required-Stop:        $local_fs
# Default-Start:        2 3 4 5
# Default-Stop:         0 1 6
# Short-Description:    Runs ThingBot Deputy rest service
### END INIT INFO

SERVICE_NAME="tbot-deputy-d"
RUNSCRIPT=/srv/tbot-deputy/tbot-deputy.sh
PID_PATH_NAME=/var/run/tbot-deputy-d.pid

case $1 in
    status)
        if [ ! -f $PID_PATH_NAME ]; then
            echo "$SERVICE_NAME is not running"
        else
            echo "$SERVICE_NAME is started"
        fi
    ;;
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -f $PID_PATH_NAME ]; then
            nohup $RUNSCRIPT 2>> /dev/null >> /dev/null &
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is already running ..."
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stoping ..."
            kill $PID;
            echo "$SERVICE_NAME stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stopping ...";
            kill $PID;
            echo "$SERVICE_NAME stopped ...";
            rm $PID_PATH_NAME
            echo "$SERVICE_NAME starting ..."
            nohup $RUNSCRIPT 2>> /dev/null >> /dev/null &
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    fix)
        if [ ! -f $PID_PATH_NAME ]; then
            echo "$PID_FILE_NAME doesn't exist, nothing to fix."
        else
            PID=$(cat $PID_PATH_NAME);
            echo "trying to stop $SERVICE_NAME ...";
            kill $PID;
            echo "cleaning PID file $PID_PATH_NAME ...";
            rm $PID_PATH_NAME
            echo "done whatever could be done.";
        fi
    ;;

esac
