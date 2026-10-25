# Release Notes

## v1.1.0 — 2026-10-25 (Initial Public Release)

This is the first public release of **StainedGlass**, a Java port of a
puzzle game originally played on Motorola 68K Macs. This release restores
the original Swing-based gameplay, modernizes the build and runtime to
JDK 21, polishes the UI, fills out the menu bar, and adds easy-to-use
packaging for Mac, Linux, and raw-jar users.

### Gameplay & Rules
- Restricted the four corner squares to primary colors at the start of
  each game, so compound colors (green, orange, purple) never appear in
  the corners (`39e84b0`).
- Fixed square selection to respond on mouse-press rather than
  mouse-click, making target squares noticeably easier to select
  (`d3e91eb`).

### UI Polish
- Rewrote the square rendering to use the game's own icon assets
  (`*_square_selected.png` / `*_square_target.png`) for move highlighting
  instead of hand-drawn ovals, added anti-aliased rendering, switched to
  `javax.swing.border.LineBorder`, and enabled the Nimbus Look & Feel
  (`bb98b1e`).
- Fixed a bug where empty (white) legal target squares did not show their
  blinking move indicator (`f591444`).
- Randomized the blink interval of each legal-move indicator to a value
  between 1 and 2 seconds for a bit of visual interest (`75d67ec`).
- Sized the game window to automatically fit the board on startup, so it
  no longer needs to be manually resized (`175bc75`).
- Moved the Moves/Hi Score status fields from an editable toolbar into a
  read-only status bar at the bottom of the window, with improved
  spacing and labels (`b270af3`).
- Wired the Moves counter up to the game model so it now increments on
  every legal move (`9e2b1eb`).
- Removed the Hi Score field until a proper high-score persistence design
  is in place (`174fb2d`).

### Menus
- Fixed **File > New game...** so it actually starts a new game board
  (`0ca117a`), and gave it a platform-independent keyboard shortcut
  (⌘N on macOS, Ctrl+N on Windows/Linux) (`1bdbd92`).
- Added **Edit > Undo**, with a platform-independent shortcut
  (⌘Z / Ctrl+Z), to undo the last move (`36e62cd`).
- Added **File > Quit** with a platform-independent shortcut to exit the
  game (`5e7c90d`).
- Added **Help > About StainedGlass**, showing the game name, version,
  author, and repository URL (`82e8f55`, `0472275`).
- Removed the empty, unused **Game** menu (`a4004bc`).

### Build & Runtime Modernization
- Restored fully working GUI gameplay on the original codebase and
  modernized the Maven build as a starting point for further work
  (`5c6ef61`).
- Migrated the build and runtime target to JDK 21, removing deprecated
  `java.util.Observable`/`Observer` usage in favor of a listener-based
  design (`97c7691`).
- Replaced a raw `Stack` and a hand-rolled `Thread` with a `Deque` and a
  Swing `Timer`, for safer undo-history management and blinking-indicator
  animation on the Event Dispatch Thread (`5c3f823`).
- Dropped the `-SNAPSHOT` suffix from the project version, since this is
  an end-user game rather than a library (`122d7e6`), and bumped the
  version to **1.1.0** to reflect the scope of changes since the original
  restoration (`4654ca3`).

### Packaging
- Configured `maven-jar-plugin` to produce a directly runnable
  `StainedGlass-<version>.jar` with an embedded `Main-Class`, so the game
  can be launched with a plain `java -jar` (`03435fe`).
- Added a `mac-installer` Maven profile that uses `jpackage` to build a
  native macOS `.dmg` installer (`b3dd2b8`), and gave it a custom
  `.icns` app icon generated from the game's own purple ("magenta")
  square asset, instead of the generic Java icon (`4a03424`).
- Added a `linux-installer` Maven profile that uses `jpackage` to build a
  native Linux installer, defaulting to `.rpm` (Fedora/RHEL) and
  overridable to `.deb` (Debian/Ubuntu) via a Maven property
  (`0d09ce8`).

### Housekeeping
- Removed a stray, unused `MusicDesignShape.java` file left over from
  early development (`bb1891f`).
- Removed leftover "joke" `exec-maven-plugin` arguments and a debug
  system property that had no place in a release build (`5e1f899`).
- Removed all Eclipse/IDE project artifacts (`.project`, `.classpath`,
  `.settings/`, `wbp-meta/`) and added a `.gitignore` covering common
  IDEs, ahead of making the repository public (`ae5e76c`).
- Clarified and expanded the project description and game rules in
  `README.md` (`31e8353`, `96d0a2e`, `d7b709b`, `5862a33`).

### Development Checkpoints
For historical reference, the following tags mark notable milestones
reached along the way to this release:

- `original_game_restored` — the original Swing GUI gameplay fully
  restored and playable again.
- `game_with_menu_items` — a working `File`/`Edit` menu with New Game,
  Undo, and Quit, platform-independent shortcuts, and a live-updating,
  read-only Moves/Hi Score status bar.
- `random_next_move_intervals` — randomized per-square blink intervals
  for legal move targets, and removal of the empty `Game` menu.
