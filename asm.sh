#!/usr/bin/env bash

current=$(pwd)
resources=${current}/backend/src/main/resources

BE=${current}/backend
PUBLIC=${resources}/public
TEMPLATES=${resources}/templates
echo "Templates dir: ${TEMPLATES}"
echo "Public dir   : ${PUBLIC}"
echo "Backend dir  : ${BE}"



echo ">>> removing old bundle"
rm -rf ${PUBLIC}
rm ${TEMPLATES}/index.html
mkdir ${PUBLIC}



echo ">>> yarn building & installing"
cd webclient

yarn install
yarn build



echo ">>> copying new bundle"
cp -R build/. ${PUBLIC}
cp build/index.html ${TEMPLATES}



echo ">>> building backend"
cd ${BE}
gradle clean build
mv ${BE}/build/libs/backend-0.0.1.jar ${current}/app.jar

