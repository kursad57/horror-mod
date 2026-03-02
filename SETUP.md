# Horror Mod Setup & Build Guide

## What is Horror Mod?

A Minecraft Forge 1.20 mod that adds horror and challenge to your game:
- **Sanity System:** Drops when taking damage, killing animals, or staying in darkness
- **Ambient Atmosphere:** Night-time whispers and scary messages
- **Ghost Entity:** Invisible floating entities that increase mob difficulty
- **Monster Boss:** Spawns elite monster when praised ("kürşad en iyi ve senden daha becerikli")
- **Wave Attacks:** 2-night monster siege if you don't praise Kürşad
- **Jump-Scare:** Cute duck image + sound when sanity is very low
- **Config:** Fully customizable settings

---

## Prerequisites

**Required:**
- Windows PowerShell 5.1 or higher (comes with Windows)
- Java 17 LTS (NOT Java 25, NOT Java 21+)
  - Download: https://adoptium.net/ → Select "Temurin 17 LTS"
  - Install to `C:\Program Files\Eclipse Adoptium\jdk-17.0.x`
- About 2-3 GB disk space

**Optional:**
- Git (for version control)
- IDE (IntelliJ IDEA Community, Eclipse, or VS Code for debugging)

---

## Step-by-Step Setup

### Step 1: Download Forge MDK

1. Go to https://files.minecraftforge.net/
2. Select **Minecraft 1.20** on the left
3. Find version **46.0.14** and click **MDK**
4. Wait for download to complete (~200 MB)
5. Extract to `C:\forge-mdk` (or anywhere you prefer)

### Step 2: Verify Java 17 Installation

Open PowerShell and run:

```powershell
java -version
```

You should see output like:
```
openjdk version "17.0.x" ...
OpenJDK Runtime Environment (build 17.0.x...)
```

If you see Java 25 or 21, you need to install Java 17.

**Set Java 17 as default:**
```powershell
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-17.0.x"
java -version  # Verify it now shows 17
```

### Step 3: Import Horror Mod Sources

Navigate to the horror-mod folder:

```powershell
cd C:\Users\KURSAD METE\Downloads\forge-1.20-46.0.14-installer\horror-mod
```

Run the import script:

```powershell
powershell -ExecutionPolicy Bypass -File .\import_to_mdk.ps1 -mdkPath "C:\forge-mdk"
```

You should see:
```
Copying horror-mod sources to MDK at: C:\forge-mdk
Copying Java sources...
Copying resources...
Done.
```

### Step 4: Build the Mod

Navigate to the MDK directory:

```powershell
cd C:\forge-mdk
```

**Ensure Java 17 is set:**
```powershell
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-17.0.x"
```

Build the mod:

```powershell
.\gradlew.bat --no-daemon build -x test
```

This will:
- Download ~1-2 GB of dependencies (first time only)
- Compile all mod sources
- Generate `build\libs\horrormod-1.0.0.jar`

**Wait time:** 5-15 minutes (first build takes longer)

### Step 5: Run Client (Test the Mod)

```powershell
cd C:\forge-mdk
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-17.0.x"
.\gradlew.bat --no-daemon runClient
```

This will:
1. Launch Minecraft 1.20
2. Load the Horror Mod (horrormod ID)
3. You can test in a creative/survival world

**First-time launch:** 3-5 minutes (Forge must setup directories)

---

## Features to Test

Once the game loads:

1. **Create a world** (Survival mode recommended)
2. **Lower your sanity:**
   - Get attacked by a mob (damage reduces sanity)
   - Kill an animal
3. **At night (gamer time 13000-23000):**
   - Listen for cave ambience (placeholder sound)
   - See fog/smoke particles around you
   - Random creepy Turkish messages appear
4. **Say in chat:** `kürşad en iyi ve senden daha becerikli`
   - A boss monster will spawn near you (24-hour cooldown)
   - OR 2-night wave attacks if you don't praise Kürshad
5. **When sanity ≤30:**
   - Jump-scare chance with cute duck image
   - Faint whisper sound

---

## Customizing the Mod

### Config File

Once the mod is installed in Minecraft, a config file is generated:
```
%APPDATA%\.minecraft\config\horrormod-common.toml
```

Editable settings:
- `sanityLowThreshold` (default 30) — trigger point for scares
- `mobAggroMultiplier` (default 1.5) — how much stronger mobs become
- `enableVisualJumpscare` (default true) — toggle duck image
- `ambientMessageChance` (default 1200) — how often creepy messages appear (1 in X ticks)
- `enableAmbientWhisper` (default true) — toggle whisper sound

### Adding Sound Files

Place audio files in the mod resources:
- `src/main/resources/assets/horrormod/sounds/night_amb.ogg` — ambient loop
- `src/main/resources/assets/horrormod/sounds/jumpscare.ogg` — big scare sound
- `src/main/resources/assets/horrormod/sounds/whisper.ogg` — subtle whisper

Then rebuild with `.\gradlew.bat build`.

### Changing Duck Image

Replace `src/main/resources/assets/horrormod/textures/gui/duck.png` with your own 128×128 PNG image.

---

## Troubleshooting

### Error: "Unsupported class file major version 69"

**Cause:** Java version mismatch (you're using Java 25+)

**Fix:**
```powershell
# Download and install Java 17 LTS
# Then set it:
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-17.0.x"
java -version
# Retry build
.\gradlew.bat clean
.\gradlew.bat --no-daemon build -x test
```

### Error: "Cannot find gradlew.bat"

**Cause:** You're not in the MDK folder

**Fix:**
```powershell
cd C:\forge-mdk
ls gradlew.bat  # Should show the file
```

### Error: "Execution policy does not allow running scripts"

**Fix:**
```powershell
powershell -ExecutionPolicy Bypass -File .\import_to_mdk.ps1 -mdkPath "C:\forge-mdk"
```

### Gradle Daemon Error / Slow Build

**Fix:**
```powershell
cd C:\forge-mdk
# Kill existing daemon
.\gradlew.bat --stop
# Rebuild
.\gradlew.bat --no-daemon build -x test
```

### Mod doesn't appear in Minecraft

**Checklist:**
1. ✓ mods.toml exists at `src/main/resources/META-INF/mods.toml`
2. ✓ modId is "horrormod" 
3. ✓ JAR file built successfully to `build\libs\`
4. ✓ Launched Minecraft 1.20 with Forge
5. ✓ Check `Mods` list in main menu

---

## Building for Distribution

To create a distributable JAR file:

```powershell
cd C:\forge-mdk
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-17.0.x"
.\gradlew.bat --no-daemon build --no-daemon
```

The mod JAR will be at:
```
C:\forge-mdk\build\libs\horrormod-1.0.0.jar
```

You can place this JAR file directly into `%APPDATA%\.minecraft\mods\` folder on any Minecraft 1.20 Forge installation.

---

## Common Questions

**Q: Can I run this on older Java versions?**
A: Forge 1.20-46.0.14 requires Java 17+. Java 25 may have compatibility issues (as you've seen). Stick to Java 17 LTS.

**Q: How do I update the mod?**
A: Edit source files in `src/main/java` or `src/main/resources`, then rebuild: `.\gradlew.bat --no-daemon build -x test`

**Q: Can I share this mod?**
A: Yes, but ensure duck.png (CC0), game assets, and sounds don't use copyrighted material. Redistribute only the JAR from `build\libs\`.

**Q: How do I add custom sounds?**
A: Place `.ogg` files in `src/main/resources/assets/horrormod/sounds/`, register in `sounds.json`, then rebuild.

---

## Getting Help

If you're stuck:
1. Check the **Troubleshooting** section above
2. Review the build output for error messages
3. Ensure Java 17 is set: `java -version` shows 17.x
4. Clear cache and retry: `rm .gradle` in MDK folder

---

**Happy modding!** 🎮👻
