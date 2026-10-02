# OpenDroid / ShizuStore

This repository now contains a foundational Android implementation of **ShizuStore**, a unified open-source app store inspired by the specification in `temp`.

## Included baseline

- Android app scaffold (Kotlin + Jetpack Compose + Material 3)
- Data-driven app catalog model (no hardcoded UI catalog)
- Remote-first catalog sync with local asset fallback
- Room cache for offline-ready app listings
- Periodic WorkManager sync scheduling
- Home, Search, and App Detail screens
- Installer abstraction with Shizuku/root/session/system/custom modes
- Version comparison utility and baseline unit tests

## Project structure

- `app/src/main/java/com/opendroid/shizustore/data` — local DB, network source, repository
- `app/src/main/java/com/opendroid/shizustore/ui` — Compose navigation and screens
- `app/src/main/java/com/opendroid/shizustore/installer` — installer state abstraction
- `app/src/main/java/com/opendroid/shizustore/sync` — background catalog sync worker
- `app/src/main/assets/catalog.json` — starter catalog data

## Next implementation phases

The `temp` specification is very large (250+ requirements), so this commit establishes the production-oriented base architecture needed to implement the remaining features incrementally: richer metadata adapters, installer engines, feed/social layer, moderation, backend APIs, advanced animations, and release pipelines.
