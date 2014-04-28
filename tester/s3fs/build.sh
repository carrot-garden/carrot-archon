#!/bin/bash

# https://github.com/s3fs-fuse/s3fs-fuse/wiki/Installation-Notes

version="1.77"

wget https://github.com/s3fs-fuse/s3fs-fuse/archive/v$version.tar.gz

tar xvzf v$version.tar.gz

cd s3fs-fuse-$version/

./autogen.sh

./configure --prefix=/usr

make

sudo make install

