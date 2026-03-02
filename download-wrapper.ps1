#!/usr/bin/env pwsh
$dist = 'https://services.gradle.org/distributions/gradle-8.4.1-bin.zip'
$out = Join-Path $PSScriptRoot 'gradle-8.4.1-bin.zip'
Write-Output "Downloading $dist to $out"
Invoke-WebRequest -Uri $dist -OutFile $out -UseBasicParsing
$tmp = Join-Path $PSScriptRoot 'gradle\wrapper\temp'
if (-Not (Test-Path $tmp)) { New-Item -ItemType Directory -Path $tmp -Force | Out-Null }
Expand-Archive -Path $out -DestinationPath $tmp -Force
$src = Join-Path $tmp 'gradle-8.4.1\lib\gradle-wrapper.jar'
$dest = Join-Path $PSScriptRoot 'gradle\wrapper\gradle-wrapper.jar'
Copy-Item -Path $src -Destination $dest -Force
Remove-Item -Path $tmp -Recurse -Force
Remove-Item -Path $out -Force
Write-Output 'gradle-wrapper.jar hazır.'
