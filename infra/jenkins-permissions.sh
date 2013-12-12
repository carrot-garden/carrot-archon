#!/bin/bash

DIR="/var/lib/jenkins"

chmod --recursive go-rwx "$DIR"
chown --recursive jenkins:jenkins "$DIR"

