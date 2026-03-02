#!/usr/bin/env sh
THIS_DIR=$(dirname "$0")
if [ -f "$THIS_DIR/gradle/wrapper/gradle-wrapper.jar" ]; then
  exec java -jar "$THIS_DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
else
  echo "gradle wrapper jar missing. Please run the downloader script or install Gradle."
  exit 1
fi
