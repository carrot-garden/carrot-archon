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
folder = os.environ['HOME'] + "/github-backup/"

if not os.path.exists(folder):
    os.makedirs(folder)
    
os.chdir(folder)

# master account @ github
username = os.environ['GITHUB_USERNAME']
password = os.environ['GITHUB_PASSWORD']

# github rest api
url_api = "https://api.github.com/user/repos"

# github project git url base
url_git = "git@github.com:carrot-garden"

# session log file
log = open("backup.log", "w")
print >> log, time.time()

def make_repo_list( page ) :

  credentials = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')
  opener = urllib2.build_opener()
  request = urllib2.Request( url_api + "?page=" + str(page) + "&per_page=10" )
  request.add_header("Authorization", "Basic %s" % credentials)
  stream = opener.open(request)

  repo_list  = json.load(stream)
  
  return repo_list

def process_repo_list( repo_list ) :
  
  for repo in repo_list:

      print "name=" + repo["name"]

      name = repo["name"]
      fork = repo["fork"]

      # non-fork means actual repo that nees backup
      if fork == True:
	continue

      print >> log, "###"

      print >> log, "name=" + name

      if os.path.exists(name):
	print >> log, "old repo; do pull"
	os.chdir(name)
	subprocess.call(["git", "pull"], stdout=log, stderr=log)
	os.chdir("./..")
      else:
	print >> log, "new repo; do clone"
	subprocess.call(["git", "clone", url_git + "/" + name + ".git"], stdout=log, stderr=log)


page = 0;
      
while True:
  
  repo_list = make_repo_list( page )
  
  if len(repo_list) == 0 :
    break
    
  process_repo_list( repo_list )
  
  page += 1
  
  
print "done"


