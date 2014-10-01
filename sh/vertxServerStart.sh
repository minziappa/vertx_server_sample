#!/bin/sh

# Batch process check script - start
declare -i batchCnt
batchCnt=`ps -ef | grep ServerBootstrap | grep -v "grep ServerBootstrap" | wc -l`
if [ $batchCnt -ge 1 ]
then
        echo "ServerBootstrap already started !!"
        exit 0
fi
# Batch batch process check script - end

JAVA_HOME=/usr/local/java
BASEDIR=/usr/local/app/vertxWebSocketServer
PROGRAM_NAME=io.sample.vertx.main.ServerBootstrap

export JAVA_HOME

for f in `find $BASEDIR/lib -type f -name "*.jar"`
do
   CLASSPATH=$CLASSPATH:$f
done

CLASSPATH=${CLASSPATH}:${BASEDIR}/bin/vertxWebSocketServer.jar

cd ${BASEDIR}/bin

JAVA_BIN=${JAVA_HOME}/bin/java
OPT="-server -Xmx128m -Xms128m -Xmn64m -classpath ${CLASSPATH} "

$JAVA_BIN $OPT ${PROGRAM_NAME} &

echo "ServerBootstrap start"

exit 0