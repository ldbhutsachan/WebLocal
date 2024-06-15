@echo off

set /p VERSION= Enter image version:

if defined VERSION GOTO :version_arg_exists

set VERSION=latest

:version_arg_exists

echo %VERSION%

echo "(1/3) Preparing to setup image registry.........."

#set IMAGE=hubporn.ldblao.la/easypass-api/mobile-border-api
set IMAGE=registry.ldblao.la/mobile-dev/reportcustomer 999

echo "(2/3) Building java package......."

echo %IMAGE%

cmd /c mvn clean dependency:tree compile package

echo "(3/3) Building image: " %IMAGE%:%VERSION%

cmd /c docker build -t %IMAGE%:%VERSION% .
cmd /c docker push %IMAGE%:%VERSION%