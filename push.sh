#!/usr/bin/env bash
# builds bot image and pushes it to dockerhub

# напишите сюда версию для деплоя
version=8           #TODO paste version here

LOCAL_NAME=bgdevs:${version}
IMAGE=yattbot/techmad:${version}
LOGIN=yattbot


echo ">>> Building local image: ${LOCAL_NAME}"
docker build -t $LOCAL_NAME .


echo ">>> Tagging image to new name: ${IMAGE}"
docker tag ${LOCAL_NAME} ${IMAGE}


echo ">>> Logging in to Docker Hub"
cat pwd.txt | docker login --username ${LOGIN} --password-stdin


echo ">>> Pushing image"
docker push ${IMAGE}


echo ">>> All done!"