@echo off
echo Rebuilding project...
call mvn clean install
echo Executing project...
call java -jar target/maven-1.0-SNAPSHOT.jar