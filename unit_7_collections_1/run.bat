@echo off
@echo Starting maven project...
@echo Cleaning and creating .jar file...
call mvn clean install
cd app
@echo Running application...
java -jar target/unit_7_app-1.0-SNAPSHOT.jar