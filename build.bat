@echo off
cd api
rem call gradlew.bat clean shadowJar --refresh-dependencies --info
call gradlew.bat clean shadowJar
cd ..\backend
rem call gradlew.bat clean shadowJar --refresh-dependencies --info
call gradlew.bat clean shadowJar
cd ..\frontend
rem call gradlew.bat clean shadowJar --refresh-dependencies --info
call gradlew.bat clean shadowJar
cd ..
