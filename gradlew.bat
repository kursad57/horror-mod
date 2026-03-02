@echo off
setlocal
set DIRNAME=%~dp0
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

if exist "%APP_HOME%gradle\wrapper\gradle-wrapper.jar" (
  java -jar "%APP_HOME%gradle\wrapper\gradle-wrapper.jar" %*
) else (
  echo gradle wrapper jar missing. Please run the included downloader script or install Gradle.
  exit /b 1
)
