package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.enemies.*;
import org.davidfabio.utils.Transform2D;

import java.util.Random;

public class EnemySpawner {
    private float timeElapsed = 0;
    private float timeLastFrame;
    private float centerX, centerY, levelWidth, levelHeight;
    private World worldReference;
    private Level levelReference;
    private Random random;

    public EnemySpawner(World world) {
        worldReference = world;
        levelReference = world.getLevel();
        centerX = worldReference.getLevel().getWidth() / 2;
        centerY = worldReference.getLevel().getHeight() / 2;
        levelWidth = worldReference.getLevel().getWidth();
        levelHeight = worldReference.getLevel().getHeight();

        random = new Random();

        timeElapsed = 0;
    }


    private Enemy spawn(Enemy.Type type, float x, float y, float spawnTime) {
        if (timeLastFrame >= spawnTime || timeElapsed < spawnTime)
            return null;

        Enemy enemy = worldReference.getEnemy(type);
        if (enemy == null)
            return null;

        switch(type) {
            case CHASER:   enemy.init(x, y, 50, 100, 2, new Color(1, 0, 0, 0.75f));        break;
            case TURRET:   enemy.init(x, y, 60, 0, 10, Color.BLUE);                        break;
            case BUBBLE:   enemy.init(x, y, 160, 20, 8, new Color(1, 0.75f, 0.8f, 0.75f)); break;
            case KAMIKAZE: enemy.init(x, y, 30, 100, 1, Color.ORANGE);                     break;
            case STAR:     enemy.init(x, y, 80, 30, 15, Color.PURPLE);                     break;
        }

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
        float x = random.nextFloat(0, levelWidth);
        float y = random.nextFloat(0, levelHeight);
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

        spawnGroupInCircle(Enemy.Type.CHASER, 12, centerX, centerY, 240, 1f, 0.15f);
        spawnGroupInCircle(Enemy.Type.CHASER, 12, centerX, centerY, 80, 5f, 0.15f);
        spawnGroupAtLine(Enemy.Type.KAMIKAZE, 8, centerX, centerY, centerX + 150, centerY + 150, 9f, 0);
        spawnGroupAtLine(Enemy.Type.KAMIKAZE, 8, centerX - 150, centerY + 150, centerX, centerY, 10.5f, 0);
        spawnGroupAtLine(Enemy.Type.KAMIKAZE, 8, centerX + 150, centerY, centerX, centerY - 150, 12f, 0);
        spawnGroupAtLine(Enemy.Type.KAMIKAZE, 8, centerX, centerY + 150, centerX + 150, centerY, 13.5f, 0);
        spawnGroupAtLine(Enemy.Type.KAMIKAZE, 8, centerX, centerY, centerX - 150, centerY - 150, 15f, 0);

        spawnGroupInCircle(Enemy.Type.BUBBLE, 3, centerX, centerY, 250, 16f, 0.5f);
        spawnGroupInCircle(Enemy.Type.KAMIKAZE, 12, centerX, centerY, 160, 20f, 0.15f);
        spawnGroupInCircle(Enemy.Type.KAMIKAZE, 12, centerX, centerY, 240, 24f, 0.15f);
        spawnAtRandomPoint(Enemy.Type.STAR, 27f);

        spawnGroupAtLine(Enemy.Type.CHASER, 12, 50, 0, 50, levelHeight, 27f, 0);
        spawnGroupAtLine(Enemy.Type.CHASER, 12, levelWidth - 50, 0, levelWidth - 50, levelHeight, 27f, 0);
        spawnGroupAtLine(Enemy.Type.TURRET, 3, 150, 100, levelWidth, 100, 34f, 1f);

        spawnGroupAtLine(Enemy.Type.KAMIKAZE, 12, 50, 50, levelWidth - 50, 50, 40f, 0.15f);
        spawnGroupAtLine(Enemy.Type.KAMIKAZE, 8, 50, centerY, levelWidth - 50, centerY, 45f, 0.15f);
        spawn(Enemy.Type.BUBBLE, centerX - 150, centerY, 48f);
        spawn(Enemy.Type.TURRET, centerX + 150, centerY, 48f);
        spawnGroupAtLine(Enemy.Type.KAMIKAZE, 12, 50, levelHeight - 50, levelWidth - 50, levelHeight - 50, 50f, 0.15f);
        spawnAtRandomPoint(Enemy.Type.STAR, 50f);

        spawnGroupAtLine(Enemy.Type.KAMIKAZE, 12, 50, 0, 50, levelHeight, 53f, 0);
        spawnGroupAtLine(Enemy.Type.KAMIKAZE, 12, levelWidth - 50, 0, levelWidth - 50, levelHeight, 56f, 0);

        spawnGroupInCircle(Enemy.Type.BUBBLE, 5, centerX, centerY, 350, 60f, 1f);
        spawnAtRandomPoint(Enemy.Type.TURRET, 62f);
        spawnAtRandomPoint(Enemy.Type.TURRET, 67f);

        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 50, centerX, centerY, 70, 0.1f);
        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 50, centerX - 150, centerY - 150, 74, 0.1f);
        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 50, centerX + 150, centerY - 150, 74, 0.1f);
        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 50, centerX + 150, centerY + 150, 74, 0.1f);
        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 50, centerX - 150, centerY + 150, 74, 0.1f);
        spawnAtRandomPoint(Enemy.Type.STAR, 85f);
    }
}