#!/bin/bash

#
# note: used by cron
#

service httpd stop

service nexus stop


yum -y update httpd


service nexus start

echo "please wait 15 sec"
sleep 15

service httpd start
