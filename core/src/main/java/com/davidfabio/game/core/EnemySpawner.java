package com.davidfabio.game.core;

import com.badlogic.gdx.graphics.Color;

public class EnemySpawner {

    public enum EnemyType {
        CHASER,
        STATIC
    }

    private float timeElapsed = 0;
    private float timeLastFrame;
    private float centerX, centerY, rightBorder, bottomBorder;
    private World worldReference;


    public EnemySpawner(World world) {
        worldReference = world;
        centerX = Settings.windowWidth / 2;
        centerY = Settings.windowHeight / 2;
        rightBorder = Settings.windowWidth;
        bottomBorder = Settings.windowHeight;
    }




    private void spawn(EnemyType type, float x, float y, float spawnTime) {
        if (timeLastFrame >= spawnTime || timeElapsed < spawnTime)
            return;

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
        worldReference.getEnemies().add(enemy);
    }

    private void spawnGroupInCircle(EnemyType type, int count, float centerX, float centerY, float radius, float spawnTime, float timeDeltaBetweenEnemies) {
        float angleDelta = (float)(2 * Math.PI) / count;
        for (int i = 0; i < count; i += 1) {
            float x = Transform2D.translateX(centerX, (i * angleDelta), radius);
            float y = Transform2D.translateY(centerY, (i * angleDelta), radius);
            spawn(type, x, y, spawnTime + (i * timeDeltaBetweenEnemies));
        }
    }

    private void spawnGroupAtPoint(EnemyType type, int count, float x, float y, float spawnTime, float timeDeltaBetweenEnemies) {
        for (int i = 0; i < count; i += 1) {
            spawn(type, x, y, spawnTime + (i * timeDeltaBetweenEnemies));
        }
    }



    public void update(float deltaTime) {
        timeElapsed += deltaTime;
        timeLastFrame = timeElapsed - deltaTime;

        spawnGroupInCircle(EnemyType.CHASER, 6, centerX, centerY, 200, 1, 0);
        spawnGroupInCircle(EnemyType.CHASER, 10, centerX, centerY, 230, 5, 0.07f);
        spawnGroupInCircle(EnemyType.CHASER, 16, centerX, centerY, 260, 10, 0.15f);

        spawn(EnemyType.STATIC, 50, 50, 10f);
        spawn(EnemyType.STATIC, rightBorder - 50, 50, 15f);
        spawn(EnemyType.STATIC, 50, bottomBorder - 50, 17f);
        spawn(EnemyType.STATIC, rightBorder - 50, bottomBorder - 50, 19f);

        spawnGroupAtPoint(EnemyType.CHASER, 4, 100, 100, 20, 0.5f);
        spawnGroupAtPoint(EnemyType.CHASER, 4, rightBorder - 100, 100, 20, 0.5f);
        spawnGroupAtPoint(EnemyType.CHASER, 4, 100, bottomBorder - 100, 20, 0.5f);
        spawnGroupAtPoint(EnemyType.CHASER, 4, rightBorder - 100, bottomBorder - 100, 20, 0.5f);
    }



}
