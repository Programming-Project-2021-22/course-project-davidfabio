```mermaid
---
title: Polygon Wars (UI Flowchart)
---
flowchart TD
    A[PolygonWarsDesktop] -->|Launches Desktop App| B[PolygonWars]
    B --> |Starts| C{MainMenuScreen}
    C -->|Play| D[GameScreen]
    C -->|Options| E[SettingsScreen]
    C -->|Highscore| F[HighscoreScreen]
    C -->|Quit| G(Exit App)
```
