package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import org.davidfabio.game.enemies.*;
import org.davidfabio.utils.Transform2D;

public class EnemySpawner {



    private float timeElapsed = 0;
    private float timeLastFrame;
    private float centerX, centerY, rightBorder, bottomBorder;
    private World worldReference;
    private Level levelReference;

    public EnemySpawner(World world) {
        worldReference = world;
        levelReference = world.getLevel();
        centerX = worldReference.getLevel().getWidth() / 2;
        centerY = worldReference.getLevel().getHeight() / 2;
        rightBorder = worldReference.getLevel().getWidth();
        bottomBorder = worldReference.getLevel().getHeight();
    }

    private Enemy spawn(Enemy.Type type, float x, float y, float spawnTime) {
        if (timeLastFrame >= spawnTime || timeElapsed < spawnTime)
            return null;

        Enemy enemy = null;
        switch(type) {
            case CHASER:
                enemy = new EnemyChaser();
                enemy.init(x, y, 50, 100, 2, new Color(1, 0, 0, 0.75f));
                break;
            case TURRET:
                enemy = new EnemyTurret();
                enemy.init(x, y, 60, 0, 10, Color.BLUE);
                break;
            case BUBBLE:
                enemy = new EnemyBubble();
                enemy.init(x, y, 160, 20, 8, new Color(1, 0.75f, 0.8f, 0.75f));
                break;
            case KAMIKAZE:
                enemy = new EnemyKamikaze();
                enemy.init(x, y, 30, 100, 1, Color.ORANGE);
                break;
            case STAR:
                enemy = new EnemyStar();
                enemy.init(x, y, 80, 30, 15, Color.PURPLE);
                break;
        }
        worldReference.getEnemies().add(enemy);
        return enemy;
    }

    private void spawnGroupInCircle(Enemy.Type type, int count, float centerX, float centerY, float radius, float spawnTime, float timeDeltaBetweenEnemies) {
        float angleDelta = (float)(2 * Math.PI) / count;
        for (int i = 0; i < count; i += 1) {
            float x = Transform2D.translateX(centerX, (i * angleDelta), radius);
            float y = Transform2D.translateY(centerY, (i * angleDelta), radius);
            spawn(type, x, y, spawnTime + (i * timeDeltaBetweenEnemies));
        }
    }

    private void spawnGroupAtPoint(Enemy.Type type, int count, float x, float y, float spawnTime, float timeDeltaBetweenEnemies) {
        for (int i = 0; i < count; i += 1) {
            spawn(type, x, y, spawnTime + (i * timeDeltaBetweenEnemies));
        }
    }

    private void spawnAtRandomPoint(Enemy.Type type, float spawnTime) {
        float x = Transform2D.getRandomX(levelReference);
        float y = Transform2D.getRandomY(levelReference);
        Enemy enemy = spawn(type, x, y, spawnTime);

        if (enemy != null)
            enemy.restrictToLevel(levelReference);
    }

    private void spawnGroupAtLine(Enemy.Type type, int count, float x1, float y1, float x2, float y2, float spawnTime, float timeDeltaBetweenEnemies) {
        float xOffset = Math.abs(x2 - x1) / count;
        float yOffset = Math.abs(y2 - y1) / count;
        for (int i = 0; i < count; i += 1) {
            float x = x1 + (i * xOffset);
            float y = y1 + (i * yOffset);
            spawn(type, x, y, spawnTime + (i * timeDeltaBetweenEnemies));
        }
    }

    
    public void update(float deltaTime) {
        timeElapsed += deltaTime;
        timeLastFrame = timeElapsed - deltaTime;

        spawnGroupAtLine(Enemy.Type.CHASER, 16, 0, 0, rightBorder, 0, 0.1f, 0);

        /*
        spawnAtRandomPoint(Enemy.Type.STAR, 5f);
        spawnAtRandomPoint(Enemy.Type.STAR, 15f);
        spawnAtRandomPoint(Enemy.Type.STAR, 30f);
        spawnAtRandomPoint(Enemy.Type.STAR, 40f);

        spawnGroupInCircle(Enemy.Type.CHASER, 16, centerX, centerY, 200, 1, 0);
        spawnGroupInCircle(Enemy.Type.KAMIKAZE, 32, centerX, centerY, 240, 5, 0.05f);
        spawnGroupInCircle(Enemy.Type.CHASER, 12, centerX, centerY, 260, 8, 0.15f);

        spawnGroupInCircle(Enemy.Type.BUBBLE, 4, centerX, centerY, 250, 13f, 0.5f);
        spawn(Enemy.Type.TURRET, rightBorder - 50, bottomBorder - 50, 19f);
        spawn(Enemy.Type.TURRET, rightBorder - 50, 50, 15f);
        spawn(Enemy.Type.TURRET, 50, bottomBorder - 50, 17f);

        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 6, 100, 100, 25, 0.5f);
        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 6, rightBorder - 100, 100, 28, 0.5f);
        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 6, 100, bottomBorder - 100, 32, 0.5f);
        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 6, rightBorder - 100, bottomBorder - 100, 32, 0.5f);

        spawnGroupInCircle(Enemy.Type.CHASER, 20, 50, 50, 200, 32, 0);
        spawnGroupInCircle(Enemy.Type.CHASER, 20, rightBorder - 50, bottomBorder - 50, 200, 32, 0);
        spawnGroupInCircle(Enemy.Type.KAMIKAZE, 32, centerX, centerY, 150, 38, 0);
        spawnGroupInCircle(Enemy.Type.KAMIKAZE, 32, centerX, centerY, 150, 41, 0);

         */
    }
}
