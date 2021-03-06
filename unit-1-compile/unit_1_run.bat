@echo off
echo Executing common terminal commands...
cd terminal
call terminal_run.bat
echo Executing ant project...
cd ../ant
call ant_run.bat
echo Executing maven project...
cd ../maven
call maven_run.bat