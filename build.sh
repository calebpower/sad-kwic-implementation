#!/bin/bash
cd api
./gradlew clean shadowJar --refresh-dependencies --info
cd ..
cd backend
./gradlew clean shadowJar --refresh-dependencies --info
cd ..
cd frontend
./gradlew clean shadowJar --refresh-dependencies --info
cd ..
