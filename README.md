# funny horor

its a funny mod

Build
```
# Ensure Java 17 is installed
.\gradlew.bat build
```

Resulting JAR: `build/libs/` (copy to `%appdata%\.minecraft\mods`).

GitHub
- Initialize repo locally: `git init && git add . && git commit -m "Initial commit"`
- Create a GitHub repo on the website or use `gh` CLI: `gh repo create` then `git push -u origin main`

Modrinth
- Prepare a release JAR (`build/libs/horrormod-1.0.0.jar`).
- Upload via Modrinth website (recommended) or use the Modrinth API/CLI.
- Include `modrinth.json` (template provided) in the repo and update metadata before uploading.
# Horror Mod — Minecraft 1.20 Forge

A horror-themed mod for Minecraft 1.20 featuring a sanity system, creepy atmospheres, ghost entities, wave attacks, and more.

## Features 🎃

- **Sanity System:** Tracking and degradation based on player actions/damage
- **Ambient Atmosphere:** Night-time whispers, ominous messages, fog effects
- **Ghost Entities:** Invisible creatures that spawn in low-light conditions
- **Monster Boss:** Elite "SpecialMonster" that spawns when praised
- **Wave Attacks:** 2-night siege if you don't praise the magic phrase
- **Jump-Scare Effects:** Cute duck image + sound when sanity is critically low
- **Fully Configurable:** All settings via `horrormod-common.toml`

## Quick Start

### Build Requirements
- Java 21 LTS
- Forge MDK 1.20-46.0.14

### Build Locally

See [SETUP.md](SETUP.md) for detailed instructions.

Quick build:
```bash
cd C:\forge-mdk
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-21..."
.\gradlew.bat --no-daemon build -x test
```

### Automatic Build via GitHub

1. Push to GitHub
2. GitHub Actions compiles automatically
3. Download JAR from Artifacts

See [MODRINTH.md](MODRINTH.md) for Modrinth publishing.

## Installation

Place JAR in `%APPDATA%\.minecraft\mods\` and launch Minecraft 1.20 with Forge.

## Configuration

Edit `%APPDATA%\.minecraft\config\horrormod-common.toml`:
- `sanityLowThreshold` — trigger fear effects (default 30)
- `mobAggroMultiplier` — mob strength multiplier (default 1.5)
- `enableVisualJumpscare` — show duck image (default true)
- `ambientMessageChance` — message rarity (default 1200)
- `enableAmbientWhisper` — whisper sounds (default true)

## In-Game Features

- **Say:** `kürşad en iyi ve senden daha becerikli` → Boss monster spawns (24h cooldown)
- **Night:** Ambient sounds, creepy messages, low sanity effects
- **Sanity:** Loses on: damage (-2x dmg taken), killing animals (-5), darkness
- **Sanity:** Gains on: eating (+6), holding torch (+1)

## Mod Structure

```
horror-mod/
├── src/main/java/com/example/horrormod/
│   ├── HorrorMod.java              (main mod class)
│   ├── client/                     (client-side events)
│   ├── entity/                     (Ghost, SpecialMonster)
│   ├── events/                     (server event handlers)
│   ├── config/                     (config spec)
│   └── sanity/                     (sanity utilities)
├── src/main/resources/
│   ├── assets/horrormod/           (textures, sounds, lang files)
│   └── META-INF/mods.toml          (mod metadata)
├── SETUP.md                        (local build guide)
├── BUILD.md                        (Gradle instructions)
├── MODRINTH.md                     (Modrinth publishing)
└── README.md                       (this file)
```

## License

NONE
