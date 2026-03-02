
param(
    [Parameter(Mandatory=$true)]
    [string]$mdkPath
)

if (-not (Test-Path $mdkPath)) {
    Write-Error "MDK path not found: $mdkPath"
    exit 1
}

$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Definition

Write-Host "Copying horror-mod sources to MDK at: $mdkPath"

$src = Join-Path $scriptRoot "src\main\java"
$res = Join-Path $scriptRoot "src\main\resources"

$destJava = Join-Path $mdkPath "src\main\java"
$destResources = Join-Path $mdkPath "src\main\resources"

New-Item -ItemType Directory -Force -Path $destJava | Out-Null
New-Item -ItemType Directory -Force -Path $destResources | Out-Null

Write-Host "Copying Java sources..."
Copy-Item -Path (Join-Path $src "*") -Destination $destJava -Recurse -Force

Write-Host "Copying resources..."
Copy-Item -Path (Join-Path $res "*") -Destination $destResources -Recurse -Force

Write-Host "Done. In the MDK folder run:"
Write-Host "  .\gradlew.bat --no-daemon runClient"
Write-Host "or build: .\gradlew.bat --no-daemon build"
