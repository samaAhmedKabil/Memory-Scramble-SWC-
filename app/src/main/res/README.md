# Memory Scramble 🃏

A configurable card-matching memory game for Android, built in Java.

## Description

Memory Scramble is a classic pairs game. Cards are placed face-down on a
grid; the player flips two cards at a time looking for matching symbols.
All pairs must be matched before the countdown timer reaches zero.

## Features

- Configurable board size (nRows × nColumns — must be even)
- Configurable countdown timer
- board_size / 2 unique symbols randomly distributed each round
- Visual timer that turns red in the last 10 seconds
- Win dialog when all pairs are matched
- Game-over dialog when the timer expires
- Clean Material Design UI

## Requirements

| Tool | Version |
|------|---------|
| Android Studio | Flamingo 2022.2.1 or newer |
| Android SDK | API 24 (Android 7.0) minimum |
| Java | JDK 8 or higher |
| Gradle | 8.0+ (managed by wrapper) |

## How to build and run

### 1. Clone the repository

```bash
git clone https://github.com/YourUsername/MemoryScramble.git
cd MemoryScramble
```

### 2. Open in Android Studio

- Launch Android Studio
- Choose **File → Open** and select the cloned folder
- Wait for Gradle sync to finish (click **Sync Now** if prompted)

### 3. Run on a device or emulator

- Connect an Android device via USB (enable Developer Options & USB
  Debugging) **or** start an AVD from **Device Manager**
- Press the ▶ **Run** button or hit **Shift + F10**

### 4. Configure and play

1. Enter **rows** and **columns** (product must be even, max 48 cells)
2. Set a **timeout** in seconds (minimum 5)
3. Tap **START GAME**
4. Tap cards to flip them — find all matching pairs before time runs out!

## Project structure

```
MemoryScramble/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/memoryscramble/
│   │   │   ├── MainActivity.java    # Board configuration screen
│   │   │   ├── GameActivity.java    # Game logic & timer
│   │   │   ├── CardModel.java       # Card data model + symbol pool
│   │   │   └── CardAdapter.java     # RecyclerView adapter for the grid
│   │   └── res/
│   │       ├── layout/
│   │       │   ├── activity_main.xml
│   │       │   ├── activity_game.xml
│   │       │   └── item_card.xml
│   │       └── values/
│   │           ├── colors.xml
│   │           ├── strings.xml
│   │           └── themes.xml
│   └── AndroidManifest.xml
└── README.md
```

## Team

| Name | Role |
|------|------|
| Member 1 | Game logic & timer |
| Member 2 | UI / XML layouts |
| Member 3 (optional) | Testing & documentation |

## License

MIT — see [LICENSE](LICENSE)
