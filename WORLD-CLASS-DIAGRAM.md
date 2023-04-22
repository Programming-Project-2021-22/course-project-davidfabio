```mermaid
---
title: Duality (Game Class Diagram)
---
classDiagram
Entity <|-- Player

Entity <|-- Enemy
Enemy <|-- EnemyBubble
Enemy <|-- EnemyChaser
Enemy <|-- EnemyKamikaze
Enemy <|-- EnemySpinner
Enemy <|-- EnemyTurret

Entity <|-- Bullet
Bullet <|-- BulletPlayer
Bullet <|-- BulletEnemy

Entity ..|> Movable
BulletPlayer ..|> Attacker
Enemy ..|> Attacker
Player ..|> Attackable
Enemy ..|> Attackable

class Entity {
    +int x
    +int y
    +float moveSpeed
    +float angle
    +init()
    +render()
}

class Player {
    +float health
    +Bullet[] bullets
    +init()
    +render()
    +update()
    +shoot()
}

class Enemy {
    +float health
    +float attackPower
    +int POINT_VALUE
    +init()
    +update()
    +spawnPickup()
    +destroy()
}

class EnemyBubble {
    +init()
    +update()
    +destroy()
}

class EnemyChaser {
    +init()
    +update()
    +render()
}

class EnemyKamikaze {
    +init()
    +update()
}

class EnemySpinner {
    +float rotationSpeed
    +init()
    +update()
    +render()
}

class EnemyTurret {
    +BulletSpawner bulletSpawner
    +init()
    +update()
    +destroy()
}

class Bullet {
    +init()
    +update()
}

class BulletEnemy {
    +float attackPower
    +init()
    +update()
}

class BulletPlayer {
    +float attackPower
    +init()
    +update()
}

note for Movable "Used to position and move Objects towards something."
class Movable {
    <<interface>>
    +isInView()
    +restrictToLevel()
    +moveTowards()
    +getAngleTowards()
    +getDistanceTo()
}

note for Attackable "Used to track health, hit damage and death."
class Attackable {
    <<interface>>
    +initializeHealth()
    +destroy()
}

note for Attacker "Used to attack Attackables."
class Attacker {
    <<interface>>
    +attack(Attackable attackable)
}

```