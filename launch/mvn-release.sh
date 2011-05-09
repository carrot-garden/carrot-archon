
git commit --all --message "ready"

mvn release:prepare --batch-mode  --show-version --update-snapshots --define skipTests

mvn release:perform --batch-mode  --show-version --update-snapshots --define skipTests



