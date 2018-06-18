#!/bin/bash

PID_FILE="${PRO_NAME}.pid"
#JVM_ARGS=(-Xmx4g -Xms1024m -XX:MaxPermSize=256m -XX:+UseConcMarkSweepGC -XX:-OmitStackTraceInFastThrow -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:logs/${PRO_NAME}_gc.log -XX:OnOutOfMemoryError="jmap -F -dump:format=b,file=java_pid%p.hprof %p" -Djava.security.egd=file:/dev/./urandom)
WAIT_TIME=60

DEFAULT_ENV=online
DEFAULT_PORT=8080

function startService()
{
    if [[ $1 =~ ".jar" ]]; then
        jar_file=$1
        application_file=$2
        option_arg=$3
    else
        jar_file=(`find . -maxdepth 1 -name '*.jar'`)
        if [[ ${#jar_file[@]} == 0 ]]; then
            echo "There is no jar file in current dir".
            return 1
        fi
        if [[ ${#jar_file[@]} > 1 ]]; then
            echo "Find more than one jar file in current dir."
            return 1
        fi

        if [ ! $1 ]; then
            application_file="--spring.profiles.active="${DEFAULT_ENV}
        else
            application_file="--spring.profiles.active="$1
        fi
    fi

    if [ -f ${PID_FILE} ]; then
        pro_pid=`cat ${PID_FILE}`

        if ps -p ${pro_pid} > /dev/null 2>&1; then
            echo "$PRO_NAME has already started. pid=$pro_pid" >&2
            return 1
        fi
    fi

    # Get spring active profile
    profile=`echo $application_file | awk -F"[=]" '{print $2}'`

    # Get port from yml file.
    if [ ! -f "application-${profile}.yml" ]; then
        echo "There is no application-${profile}.yml in current dir".
        return 1
    fi

    parse_info_from_yml "application-${profile}.yml"

    if [ ! $port ]; then
        port=${DEFAULT_PORT}
    fi

    # start server
    echo start ${jar_file} at port=${port} with profile=${profile}

    start_cmd=("nohup" "java" "${JVM_ARGS[@]}" "-jar" "$jar_file" "$application_file" "$option_arg")
    "${start_cmd[@]}" &
    pro_pid=$!

    wait_s=0

    while [ ${wait_s} -lt ${WAIT_TIME} ]
    do
        echo waiting ${PRO_NAME} to start, elapse=${wait_s}
        wait_s=$((wait_s+1))
        sleep 1
        if netstat -tuplen | grep ${port} | grep ${pro_pid} > /dev/null; then
            echo ${pro_pid} > ${PID_FILE}
            echo succeed to start ${PRO_NAME}
            return 0
        fi
    done

    if ps -p ${pro_pid} > /dev/null 2>&1; then
        kill -9 ${pro_pid}
    fi

    echo fail to start ${PRO_NAME}
    return 1
}


function parse_info_from_yml {
   local s='[[:space:]]*' w='[a-zA-Z0-9_]*' fs=$(echo @|tr @ '\034')
   results=`sed -ne "s|^\($s\):|\1|" \
        -e "s|^\($s\)\($w\)$s:$s[\"']\(.*\)[\"']$s\$|\1$fs\2$fs\3|p" \
        -e "s|^\($s\)\($w\)$s:$s\(.*\)$s\$|\1$fs\2$fs\3|p"  $1 |
   awk -F$fs '{
      indent = length($1)/2;
      vname[indent] = $2;
      output = ""
      for (i in vname) {if (i > indent) {delete vname[i]}}
      if (length($3) > 0) {
         vn=""; for (i=0; i<indent; i++) {vn=(vn)(vname[i])("_")}
         if (vn == "server_" && $2 == "port") {
            print("port="$3)
         }
         if (vn == "bcm_" && $2 == "scope") {
            print("scope="$3)
         }
         if (vn == "bcm_" && $2 == "clusterId") {
            print("clusterId="$3)
         }
         if (vn == "bcm_" && $2 == "instanceId") {
            print("instanceId="$3)
         }
      }
   }'`

   for result in ${results[@]}
   do
       export_command=`echo $result | awk -F[=] '{print "export "$1"="$2}'`
       $export_command
   done
}


function stopService()
{
    if [ -f $2 ]; then
        pid=`cat $2`
        if ps -p ${pid} > /dev/null 2>&1; then
            # kill all child processes
            ps awx -o "%p %P" | grep -w $pid | awk '{print $1}' | xargs kill > /dev/null 2>&1

            wait_s=0
            while [ ${wait_s} -lt ${WAIT_TIME} ]
            do
                echo waiting $1 to stop, elapse=${wait_s}
                wait_s=$((wait_s+1))
                sleep 1

                if ! ps -p ${pid} > /dev/null; then
                    echo succeed to stop $1
                    rm $2
                    return 0
                fi
            done
            echo fail to stop $1
            return 1
        fi
        rm $2
    fi
    echo $1 has not started.
    return 0
}

function start()
{
    startService $1 $2 $3

    success=$?
}

function stop()
{
    stopService ${PRO_NAME} ${PID_FILE}
}

command -v lockfile-create >/dev/null 2>&1 && {
    LOCK_CMD="lockfile-create run"
    UNLOCK_CMD="lockfile-remove run"
} || {
    command -v lockfile >/dev/null 2>&1 && {
        LOCK_CMD="lockfile run.lock"
        UNLOCK_CMD="rm -f run.lock"
    } || {
        LOCK_CMD=""
        UNLOCK_CMD=""
    }
}

function lock()
{
    echo locking this script
    ${LOCK_CMD}
}

function unlock()
{
    echo unlocking this script
    ${UNLOCK_CMD}
}