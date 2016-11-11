#!/bin/bash

if [ Msys = '$(uname -o)2>/dev/null' ]; then #git for windows
	DOCKER_HOST=$(docker-machine ip default)
elif [ Darwin = "$(uname -s)" ]; then #mac
	DOCKER_HOST=$(docker-machine ip default)
else #other
	DOCKER_HOST=$(hostname)
fi
pwd=$(pwd)
WF_URL="http://$DOCKER_HOST:8088/engine-rest"

find . -name "*.properties.template" | while IFS= read -r pathname; do
    dirname=$(dirname "$pathname")
    sed "s+DOCKER_HOST+$DOCKER_HOST+g; s+WF_URL+$WF_URL+g" "$pathname" > "$dirname/gradle.properties"
done
