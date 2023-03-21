#!/bin/bash

echo "(1/3) Preparing to setup image registry.........."

#IMAGE=hubporn.ldblao.la/easypass-api/mobile-border-api
IMAGE=registry.ldblao.la/mobile-dev/reportcustomer

echo "(2/3) Building java package......."

echo $IMAGE

if [ -n "$1" ]; then VERSION=$1; else VERSION="lastest";fi

echo "(3/3) Building image:  $IMAGE:$VERSION"

mvn clean dependency:tree compile package

docker build --platform linux/amd64 -t $IMAGE:$VERSION .
docker push $IMAGE:$VERSION
