@echo off
echo Main-Class: ua.com.shyrkov.Main>MANIFEST.MF
echo Rebuilding project...
call ant clean
echo Compiling project...
call ant compile
echo Building jar file...
call ant jar
echo Executing jar...
call ant run