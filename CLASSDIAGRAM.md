´´´mermaid
---
title: Duality
---
classDiagram
Entity <|-- Player
Entity <|-- Enemy
Enemy <|-- EnemyChaser
Enemy <|-- EnemyStatic
Entity <|-- Bullet
Bullet <|-- BulletPlayer
Bullet <|-- BulletEnemy
class World {
+Player player
+ArrayList<Enemy> enemies
+BulletEnemy[] enemyBullets
+Level level
+Score score
+World()
+update()
+render()
}
note for World "Container for all World elements\nProvides functions to update and Render World State."

    Entity ..|> Movable
    BulletPlayer ..|> Attacker
    Enemy ..|> Attacker
    Player ..|> Attackable
    Enemy ..|> Attackable

    class Entity {
        +int x
        +int y
        +Polarity polarity
        +float moveSpeed
        +init()
        +render()
    }

    note for Player "Player class that the user controls."
    class Player {
        +float health
        +BulletPlayer[] bullets
        +init()
        +render()
        +update(float deltaTime)
        +shoot()
        +switchPolarity()
    }

    class Enemy {
        +float health
        +float attackPower
        +int POINT_VALUE
        +init()
        +update(float deltaTime)
        +shootTowardsPlayer()
        +shoot()
    }

    note for EnemyChaser "Enemies that chase the Player."
    class EnemyChaser {
        +init()
        +update(float deltaTime)
        +render()
    }

    note for EnemyStatic "Still enemies that explode on death."
    class EnemyStatic {
        +init()
        +update(float deltaTime)
        +destroy()
    }

    class Bullet {
        +boolean toDestroyNextFrame
        +init()
        +update(float deltaTime)
    }

    class BulletEnemy {
        +float attackPower
        +init()
        +update(float deltaTime)
    }

    class BulletPlayer {
        +float attackPower
        +init()
        +update(float deltaTime)
    }

    note for Movable "Used to position and move Objects towards something."
    class Movable {
        <<interface>>
        +isInView()
        +moveTowards()
        +getAngleTowards()
        +getDistanceTo()
    }

    note for Attackable "Used to track health, hit damage and death."
    class Attackable {
        <<interface>>
        +playHitSound()
        +playDestructionSound()
        +initializeHealth()
        +destroy(World world)
    }

    note for Attacker "Used to attack Attackables."
    class Attacker {
        <<interface>>
        +attack(Attackable attackable)
    }
´´´