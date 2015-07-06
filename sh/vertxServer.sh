#!/bin/sh

DAEMON_HOME=/usr/local/app
DAEMON_USER=cy_tomcat
PID_FILE=$DAEMON_HOME/run/vertxWebSocket.pid
JAVA_HOME=/usr/local/java
BASEDIR=/usr/local/app/vertxWebSocketServer
PROGRAM_NAME=io.sample.vertx.main.VertxWebSocketServerMain

export JAVA_HOME

for f in `find $BASEDIR/libs -type f -name "*.jar"`
do
        CLASSPATH=$CLASSPATH:$f
done
CLASSPATH=${CLASSPATH}:${BASEDIR}/bin/vertxWebSocketServer.jar

case "$1" in
        "start")
        $DAEMON_HOME/jsvc -user $DAEMON_USER -home $JAVA_HOME \
        -wait 10 -pidfile $PID_FILE -outfile $DAEMON_HOME/logs/vertxWebSocket.out \
        -server -Xmx128m -Xms128m -Xmn64m \
        -errfile '&1' -cp $CLASSPATH $PROGRAM_NAME
        #To get a verbose JVM
        #-verbose \
        #To get a debug of jsvc.
        #-debug \
        echo "Daemon start"
        exit $?
        ;;

        stop)
        $DAEMON_HOME/jsvc -stop -pidfile $PID_FILE $PROGRAM_NAME
        echo "Daemon stop"
        exit $?
        ;;

        *)
        echo "Usage vertxWebSocket.sh start/stop"
        exit 1;;
esac

exit 0