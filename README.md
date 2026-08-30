# Fruit Salad

A Java/Android game project built with **LibGDX** and a multi-module Gradle architecture.

## Project Structure

- `core/` — platform-independent game logic
- `android/` — Android launcher, resources, native dependencies, and packaging
- Gradle-based dependency and build management

## Technology Stack

- Java
- Android
- LibGDX 1.10.0
- Box2D
- Box2D Lights
- Ashley ECS
- GDX AI
- Bullet physics bindings
- Gradle

## Engineering Highlights

- Separates platform-specific Android code from reusable game logic
- Uses LibGDX's Android backend and native libraries
- Includes physics, entity-component-system, AI, controller, font, and lighting dependencies
- Packages game assets through the Android project
- Keeps generated APK/AAB release artifacts out of source control

## Build

The project uses the Gradle wrapper.

```bash
./gradlew android:assembleDebug
```

On Windows:

```powershell
.\gradlew.bat android:assembleDebug
```

The generated APK is placed in the Android module's build output directory and is intentionally excluded from Git.

## Portfolio Notes

This repository demonstrates Android game-development fundamentals, Gradle multi-module organization, native dependency integration, asset management, and a game architecture that separates shared logic from the Android launcher.

## Roadmap

- Modernize Android/Gradle dependencies
- Add gameplay screenshots and a short demo GIF
- Add automated smoke tests for core game logic
- Document gameplay rules and architecture in more detail

## Author

**Mehmet Zülfü Deniz**  
Software Developer · Android Developer · IT & Technical Support
