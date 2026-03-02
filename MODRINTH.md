# Modrinth Release Guide

To auto-publish Horror Mod to Modrinth:

## 1. Create GitHub Repository

```bash
cd /path/to/horror-mod
git init
git add .
git commit -m "Initial Horror Mod commit"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/horror-mod.git
git push -u origin main
```

## 2. Create Modrinth Project

1. Go to https://modrinth.com/mods/create
2. Fill in:
   - **Name:** Horror Mod
   - **Summary:** Adds creepy horror atmosphere, sanity system, ghosts, and monsters to Minecraft 1.20
   - **Description:** (see GitHub README)
   - **Version:** 1.0.0
   - **Game versions:** 1.20
   - **Loaders:** Forge
   - **License:** MIT (or your choice)

## 3. Get Modrinth API Key

1. Go to https://modrinth.com/settings/account
2. Copy your **API Token**

## 4. Add GitHub Secrets

1. Go to your GitHub repo → **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add:
   - **Name:** `MODRINTH_TOKEN`
   - **Value:** (paste your API token)

## 5. Update Workflow (Optional)

To auto-publish to Modrinth on release tags:

Add to `.github/workflows/build.yml` (after build step):

```yaml
    - name: Publish to Modrinth
      if: startsWith(github.ref, 'refs/tags/')
      uses: gradle-nexus/publish-action@v1
      with:
        modrinth-token: ${{ secrets.MODRINTH_TOKEN }}
        modrinth-id: <your-project-id>
        modrinth-version: ${{ github.ref_name }}
```

## 6. Trigger Build

Push code to GitHub:

```bash
git add .
git commit -m "Add Horror Mod mod"
git push
```

Go to **Actions** tab in GitHub — build will start automatically.

Once built, download JAR from **Artifacts** and upload manually to Modrinth, or set up the workflow above for auto-publish.

---

For help: https://modrinth.com/docs/modpacks/sharing_mods
