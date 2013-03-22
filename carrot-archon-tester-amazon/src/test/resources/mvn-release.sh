#
# Copyright (C) 2010-2013 Andrei Pozolotin <Andrei.Pozolotin@gmail.com>
#
# All rights reserved. Licensed under the OSI BSD License.
#
# http://www.opensource.org/licenses/bsd-license.php
#


rm release.properties pom.xml.releaseBackup

git commit --all --message "ready"

mvn release:prepare --batch-mode  --show-version --update-snapshots --define skipTests

mvn release:perform --batch-mode  --show-version --update-snapshots --define skipTests

######################

mvn release:rollback --batch-mode  --show-version --update-snapshots --define skipTests

git tag -d carrot-archon-1.0.20
git push origin :refs/tags/carrot-archon-1.0.20



