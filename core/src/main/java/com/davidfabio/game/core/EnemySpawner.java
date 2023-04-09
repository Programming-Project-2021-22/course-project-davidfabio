package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;

public class EnemySpawner {

    public enum EnemyType {
        CHASER,
        STATIC
    }


    private float timeLastFrame;


    private void spawn(EnemyType type, float x, float y, World world) {
            Enemy enemy = null;
            switch(type) {
                case CHASER:
                    enemy = new EnemyChaser();
                    enemy.init(x, y, 50, 100, 3, new Color(1, 0, 0, 0.75f));
                    break;
                case STATIC:
                    enemy = new EnemyStatic();
                    enemy.init(x, y, 60, 0, 10, Color.BLUE);
                    break;
            }
            world.getEnemies().add(enemy);
    }


    private void spawnAt(EnemyType type, float x, float y, float spawnTime, World world) {
        float time = world.getTimeElapsed();

        if (timeLastFrame < spawnTime && time >= spawnTime)
            spawn(type, x, y, world);
    }



    public void update(float deltaTime, World world) {
        float time = world.getTimeElapsed();
        timeLastFrame = time - deltaTime;


        for (int i = 0; i < 5; i += 1) {
            spawnAt(EnemyType.CHASER, 0, 0, i * 0.5f, world);
            spawnAt(EnemyType.CHASER, Settings.windowWidth, Settings.windowHeight, i * 0.5f, world);
            spawnAt(EnemyType.CHASER, 0, Settings.windowHeight, i * 0.5f, world);
            spawnAt(EnemyType.CHASER, Settings.windowWidth, 0, i * 0.5f, world);
        }



        float delta = (float)(2 * Math.PI) / 36;
        float centerX = Settings.windowWidth / 2;
        float centerY = Settings.windowHeight / 2;
        for (int i = 0; i < 36; i += 2) {
            float x = Transform2D.translateX(centerX, (i * delta), 200);
            float y = Transform2D.translateY(centerY, (i * delta), 200);
            spawnAt(EnemyType.CHASER, x, y, 10f + (i * 0.05f), world);
        }

        spawnAt(EnemyType.STATIC, 100, 100, 15f, world);
        spawnAt(EnemyType.STATIC, Settings.windowWidth - 100, 100, 15f, world);
        spawnAt(EnemyType.STATIC, 100, Settings.windowHeight - 100, 15f, world);
        spawnAt(EnemyType.STATIC, Settings.windowWidth - 100, Settings.windowHeight - 100, 15f, world);
    }



}
