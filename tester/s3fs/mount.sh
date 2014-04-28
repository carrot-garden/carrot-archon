#!/bin/bash

# https://github.com/s3fs-fuse/s3fs-fuse/wiki/Fuse-Over-Amazon

/usr/bin/s3fs trader /home/work/trader -ouse_cache=/home/work/trader-cache
