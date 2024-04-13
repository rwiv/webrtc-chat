#! /bin/bash

cd ..

rm -rf ./src/main/resources/dist
cd ./gui
pnpm build:prod && mv ./dist ../src/main/resources
