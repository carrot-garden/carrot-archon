#!/bin/bash

#
# note: used by cron
#

service httpd stop

service jenkins stop


yum -y update httpd jenkins


service jenkins start

echo "please wait 15 sec"
sleep 15

service httpd start
