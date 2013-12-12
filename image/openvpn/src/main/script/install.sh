#!/bin/bash
#
# Copyright (C) 2010-2012 Andrei Pozolotin <Andrei.Pozolotin@gmail.com>
#
# All rights reserved. Licensed under the OSI BSD License.
#
# http://www.opensource.org/licenses/bsd-license.php
#


echo "##################################################"

echo "project : ${project.artifactId}"

echo "##################################################"

pwd

echo "##################################################"

ls -las

echo "##################################################"

apt-get --yes update
apt-get --yes install mc tar wget zip unzip openvpn secure-delete
apt-get --yes upgrade
apt-get --yes dist-upgrade

echo "##################################################"
												
ADMIN_GROUP="ubuntu"
ADMIN_USER="ubuntu"

echo "##################################################"

WORK=$(dirname $0 )

cp -f $WORK/vpn.conf /etc/openvpn/vpn.conf
cp -f $WORK/key.txt  /etc/openvpn/key.txt

chown -R root:root /etc/openvpn/
chmod -R o-rwx     /etc/openvpn/

cp -f $WORK/rc.local /etc/rc.local

chmod u+x /etc/rc.local

srm -r -l -v $WORK

echo "##################################################"

echo "### schedule shutdown"

shutdown -r -f +1 "first reboot"

echo "##################################################"

sleep 3s

exit 0
