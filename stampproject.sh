#!/bin/sh

REPO_NAME=$1
PROJECT_NAME=$2
PACKAGE_NAME=$3

mkdir $REPO_NAME
cd $REPO_NAME
git init

git remote add springstarter http://github.com/SprintHive/springstarter
git remote add origin http://github.com/SprintHive/$REPO_NAME

git fetch springstarter ground-zero
git checkout ground-zero
git checkout -b dev

fgrep -rlI "com.sprinthive.starter" . | xargs sed -i '' "s/com\.sprinthive\.starter/com.sprinthive.$PACKAGE_NAME/g"
fgrep -rlI "spring-starter" . | xargs sed -i '' "s/spring-starter/$PROJECT_NAME/g"
fgrep -rlI "springstarter" . | xargs sed -i '' "s/springstarter/$PROJECT_NAME/g"

mv src/main/java/com/sprinthive/starter src/main/java/com/sprinthive/$PACKAGE_NAME
mv src/test/java/com/sprinthive/starter src/test/java/com/sprinthive/$PACKAGE_NAME
