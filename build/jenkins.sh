#!/bin/bash

echo "### init"
date

echo "### url"
echo "$JENKINS_URL"

echo "### pwd"
pwd

echo "### git status"
git status

echo "### git diff tree"
git diff-tree -r --name-only HEAD

LIST=$( \
	git diff-tree -r --name-only HEAD | \
	egrep '/src/|/pom.xml' | \
	sed -e 's|/src/.*||' -e 's|/pom.xml||' -e 's|^.*/||' | \
	sort | uniq \
) 

for PROJ in $LIST ; do

	echo "### project"
	echo "$PROJ"
		
	URL="$JENKINS_URL/job/$PROJ/build?token=token"

	echo "### trigger"
	wget \
	--output-document=/dev/null \
	--auth-no-challenge \
	--http-user=$TRIG_USER \
	--http-password=$TRIG_PASS \
	"$URL"
			
done

echo "### done"
date

