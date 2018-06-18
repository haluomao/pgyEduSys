#!/bin/bash

#set -x # set -o xtrace
#set -u # set -o nounset
set -e # set -o errexit

PRO_NAME="pgyedu"
LOG_FILE="logs/pgy_console.log"

source ./run_common.sh

lock
trap unlock EXIT
trap unlock INT

case C"$1" in
Cstart)
    start $2 $3 $4
    ;;
Cstop)
    stop
    ;;
Crestart)
    stop && start $2 $3 $4
    ;;
C*)
    echo "Usage: $0 {start|stop|restart} online"
    ;;
esac

rc=$?
exit $rc