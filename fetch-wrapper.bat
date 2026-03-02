@echo off
echo Downloading Gradle distribution...
curl -L -o gradle-8.4.1-bin.zip "https://services.gradle.org/distributions/gradle-8.4.1-bin.zip"
echo Extracting gradle-wrapper.jar...
powershell -Command "Expand-Archive -Path gradle-8.4.1-bin.zip -DestinationPath .\gradle\wrapper\temp -Force"
copy .\gradle\wrapper\temp\gradle-8.4.1\lib\gradle-wrapper.jar .\gradle\wrapper\gradle-wrapper.jar /Y
rmdir /s /q .\gradle\wrapper\temp
del gradle-8.4.1-bin.zip
echo Done.
