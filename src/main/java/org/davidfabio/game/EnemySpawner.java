package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.enemies.*;
import org.davidfabio.utils.Transform2D;

import java.util.Random;

public class EnemySpawner {
    private int MAX_ACTIVE_ENEMIES = 24;
    private float DELAY_BETWEEN_SPAWNS = 3.5f;

    private float timeElapsed = 0;
    private float timeLastFrame;
    private float timeElapsedSinceLastSpawn = 0;
    private float centerX, centerY, rightBorder, bottomBorder;
    private World worldReference;
    private Level levelReference;

    private Random random;

    private enum SpawnFormation {
        CIRCLE,
        LINE,
        RANDOM
    }


    public EnemySpawner(World world) {
        worldReference = world;
        levelReference = world.getLevel();
        centerX = worldReference.getLevel().getWidth() / 2;
        centerY = worldReference.getLevel().getHeight() / 2;
        rightBorder = worldReference.getLevel().getWidth();
        bottomBorder = worldReference.getLevel().getHeight();

        random = new Random();
    }


    public void update(float deltaTime) {
        timeElapsed += deltaTime;
        timeElapsedSinceLastSpawn += deltaTime;

        if (timeElapsedSinceLastSpawn < DELAY_BETWEEN_SPAWNS)
            return;
        if (getActiveEnemiesCount() > MAX_ACTIVE_ENEMIES)
            return;


        // spawn new enemies
        spawnGroup();
        timeElapsedSinceLastSpawn = 0f;
    }




    private Enemy spawn(Enemy.Type type, float x, float y) {
        Enemy enemy = worldReference.getEnemy(type);
        if (enemy == null)
            return null;

        switch(type) {
            case CHASER:   enemy.init(x, y, 50, 100, 2, new Color(1, 0, 0, 0.75f)); break;
            case TURRET:   enemy.init(x, y, 60, 0, 10, Color.BLUE); break;
            case BUBBLE:   enemy.init(x, y, 160, 20, 9, new Color(1, 0.75f, 0.8f, 0.75f)); break;
            case KAMIKAZE: enemy.init(x, y, 30, 100, 1, Color.ORANGE); break;
            case STAR:     enemy.init(x, y, 80, 30, 15, Color.PURPLE); break;
        }
        enemy.restrictToLevel(levelReference);

        return enemy;
    }



    private void spawnGroup() {
        float radius = random.nextFloat(32, 256);
        float centerX = random.nextFloat(radius, levelReference.getWidth() - radius);
        float centerY = random.nextFloat(radius, levelReference.getHeight() - radius);
        Enemy.Type enemyType = getRandomEnemyType();
        SpawnFormation spawnFormation = getRandomSpawnType();

        int enemyCount = 1;
        switch (enemyType) {
            case CHASER:   enemyCount = random.nextInt(6, 12); break;
            case TURRET:   enemyCount = random.nextInt(1, 3); break;
            case BUBBLE:   enemyCount = random.nextInt(1, 3); break;
            case KAMIKAZE: enemyCount = random.nextInt(6, 22); break;
            case STAR:     enemyCount = 1; break;
        }

        switch (spawnFormation) {
            case CIRCLE: {
                float angleDelta = (float)(2 * Math.PI) / enemyCount;
                for (int i = 0; i < enemyCount; i += 1) {
                    float x = Transform2D.translateX(centerX, (i * angleDelta), radius);
                    float y = Transform2D.translateY(centerY, (i * angleDelta), radius);
                    spawn(enemyType, x, y);
                }
                break;
            }

            case LINE: {
                float x1 = random.nextFloat(0, levelReference.getWidth());
                float x2 = random.nextFloat(0, levelReference.getWidth());
                float y1 = random.nextFloat(0, levelReference.getHeight());
                float y2 = random.nextFloat(0, levelReference.getHeight());
                float xOffset = Math.abs(x2 - x1) / enemyCount;
                float yOffset = Math.abs(y2 - y1) / enemyCount;
                for (int i = 0; i < enemyCount; i += 1) {
                    float x = x1 + (i * xOffset);
                    float y = y1 + (i * yOffset);
                    spawn(enemyType, x, y);
                }
                break;
            }

            case RANDOM: {
                for (int i = 0; i < enemyCount; i += 1) {
                    float x = Transform2D.getRandomX(levelReference);
                    float y = Transform2D.getRandomY(levelReference);
                    spawn(enemyType, x, y);
                }
                break;
            }
        }

    }


    private int getActiveEnemiesCount() {
        int res = 0;
        for (Enemy enemy : worldReference.getEnemies())
            if (enemy.getIsActive())
                res += 1;

        return  res;
    }

    private Enemy.Type getRandomEnemyType() {
        int enemyTypesCount = Enemy.Type.values().length;
        int rand = random.nextInt(enemyTypesCount);
        return Enemy.Type.values()[rand];
    }

    private SpawnFormation getRandomSpawnType() {
        int spawnTypesCount = SpawnFormation.values().length;
        int rand = random.nextInt(spawnTypesCount);
        return SpawnFormation.values()[rand];
    }

    




    /*
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
     */

}




