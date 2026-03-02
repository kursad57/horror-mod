Build & Run Horror Mod

Prerequisites:
- Java (recommended: Java 17 LTS; Java 21 or higher may have compatibility issues)
- Forge MDK 1.20-46.0.14 from https://files.minecraftforge.net/

Quick Setup:

1) Download and extract Forge MDK 1.20-46.0.14 to `C:\forge-mdk`

2) Run import script in PowerShell (from horror-mod folder):

```powershell
powershell -ExecutionPolicy Bypass -File .\import_to_mdk.ps1 -mdkPath "C:\forge-mdk"
```

3) Run the mod client:

```powershell
cd C:\forge-mdk
.\gradlew.bat --no-daemon runClient
```

First build takes 5-15 minutes as Gradle downloads dependencies.

Troubleshooting:

**Java version error (class file major version 69)**
- Use Java 17 LTS instead of Java 25+
- Set JAVA_HOME=%JAVA_17_HOME% in PowerShell before building

**Execution policy blocked**
- Use: `powershell -ExecutionPolicy Bypass -File .\import_to_mdk.ps1 ...`

**Gradle build fails**
- Clear cache: `rm .gradle` in MDK folder, retry
- Ensure Java 17 is set in PATH or JAVA_HOME
