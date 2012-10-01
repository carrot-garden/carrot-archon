#!/usr/bin/python

#
# note: used by cron
#
  
import os
import urllib2
import json
import base64
import subprocess
import time

# backup location
folder = "/var/lib/nexus/github"
os.chdir(folder)

# barchart-builder account @ github
username = "xxx"
password = "xxx"

# github rest api
url_api = "https://api.github.com/orgs/barchart/repos"

# github project git url base
url_git = "git@github.com:barchart"

credentials = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')
opener = urllib2.build_opener()
request = urllib2.Request(url_api)
request.add_header("Authorization", "Basic %s" % credentials)
stream = opener.open(request)

repo_list  = json.load(stream)

log = open("backup.log", "w")

print >> log, time.time()

for repo in repo_list:

    name = repo["name"]
    fork = repo["fork"]

    # non-fork means actual barchart repo that nees backup
    if fork == True:
      continue

    print >> log, "###"

    print >> log, name

    if os.path.exists(name):
      print >> log, "repo found; do pull"
      os.chdir(name)
      subprocess.call(["git", "pull"], stdout=log, stderr=log)
      os.chdir("./..")
    else:
      print >> log, "new repo; do clone"
      subprocess.call(["git", "clone", url_git + "/" + name + ".git"], stdout=log, stderr=log)
