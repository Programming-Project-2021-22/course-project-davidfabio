package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;

public class EnemySpawner {

    public enum EnemyType {
        CHASER,
        STATIC
    }

    private void spawn(EnemyType type, int count, World world) {
        for (int i = 0; i < count; i += 1) {
            Enemy enemy = null;
            switch(type) {
                case CHASER:
                    enemy = new EnemyChaser();
                    enemy.init(Transform2D.getRandomX(), Transform2D.getRandomY(), 50, 100, 4, new Color(1, 0, 0, 0.75f));
                    break;
                case STATIC:
                    enemy = new EnemyStatic();
                    enemy.init(Transform2D.getRandomX(), Transform2D.getRandomY(), 60, 0, 10, Color.BLUE);
                    break;
            }
            world.getEnemies().add(enemy);
        }
    }

    private void spawnAt(EnemyType type, int count, float spawnTime, float deltaTime, World world) {
        float time = world.getTimeElapsed();
        float timeLastFrame = time - deltaTime;

        if (timeLastFrame < spawnTime && time >= spawnTime)
            spawn(type, count, world);
    }



    public void update(float deltaTime, World world) {
        spawnAt(EnemyType.CHASER, 10, 1f, deltaTime, world);
        spawnAt(EnemyType.STATIC, 3, 4f, deltaTime, world);
        spawnAt(EnemyType.STATIC, 3, 8f, deltaTime, world);
        spawnAt(EnemyType.CHASER, 15, 15f, deltaTime, world);
        spawnAt(EnemyType.STATIC, 3, 22f, deltaTime, world);
        spawnAt(EnemyType.CHASER, 5, 22f, deltaTime, world);
        spawnAt(EnemyType.CHASER, 5, 25f, deltaTime, world);
    }



}
