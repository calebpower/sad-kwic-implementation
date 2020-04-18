@echo off
cd api
call gradlew.bat clean shadowJar --refresh-dependencies --info
cd ..\backend
call gradlew.bat clean shadowJar --refresh-dependencies --info
cd ..\frontend
call gradlew.bat clean shadowJar --refresh-dependencies --info
cd ..
