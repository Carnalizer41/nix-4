@echo off
@echo Starting maven project...
@echo Cleaning and creating .jar file...
call mvn clean install
@echo Running application...
java -jar target/unit_9_ionio_1.jar