#!/bin/bash

NAME="nexus"
PROG="/usr/lib/nexus"
WORK="/var/lib/nexus"

chown --recursive $NAME:$NAME $PROG
chmod --recursive o-rwx $PROG

chown --recursive $NAME:$NAME $WORK
chmod --recursive o-rwx $WORK

groupadd --system --force $NAME
useradd --system --gid $NAME --home $WORK $NAME
