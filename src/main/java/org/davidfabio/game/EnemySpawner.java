package org.davidfabio.game;

import com.badlogic.gdx.graphics.Color;
import org.davidfabio.utils.Transform2D;

public class EnemySpawner {



    private float timeElapsed = 0;
    private float timeLastFrame;
    private float centerX, centerY, rightBorder, bottomBorder;
    private World worldReference;

    public EnemySpawner(World world) {
        worldReference = world;
        centerX = worldReference.getLevel().getWidth() / 2;
        centerY = worldReference.getLevel().getHeight() / 2;
        rightBorder = worldReference.getLevel().getWidth();
        bottomBorder = worldReference.getLevel().getHeight();
    }

    private void spawn(Enemy.Type type, float x, float y, float spawnTime) {
        if (timeLastFrame >= spawnTime || timeElapsed < spawnTime)
            return;

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
            case SPINNER:
                enemy = new EnemySpinner();
                enemy.init(x, y, 70, 100, 10, Color.GREEN);
                break;
        }
        worldReference.getEnemies().add(enemy);
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



    public void update(float deltaTime) {
        timeElapsed += deltaTime;
        timeLastFrame = timeElapsed - deltaTime;

        spawn(Enemy.Type.CHASER, 200, 200, 0.1f);

        /*
        spawnGroupInCircle(Enemy.Type.CHASER, 6, centerX, centerY, 200, 1, 0);
        spawnGroupInCircle(Enemy.Type.KAMIKAZE, 5, centerX, centerY, 240, 5, 0.33f);
        spawnGroupInCircle(Enemy.Type.CHASER, 16, centerX, centerY, 260, 10, 0.15f);

        spawn(Enemy.Type.TURRET, 50, 50, 10f);
        spawnGroupInCircle(Enemy.Type.BUBBLE, 2, centerX, centerY, 150, 17f, 1.5f);
        spawn(Enemy.Type.TURRET, rightBorder - 50, bottomBorder - 50, 19f);
        spawn(Enemy.Type.TURRET, rightBorder - 50, 50, 15f);
        spawn(Enemy.Type.TURRET, 50, bottomBorder - 50, 17f);

        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 6, 100, 100, 25, 0.5f);
        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 6, rightBorder - 100, 100, 28, 0.5f);
        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 6, 100, bottomBorder - 100, 32, 0.5f);
        spawnGroupAtPoint(Enemy.Type.KAMIKAZE, 6, rightBorder - 100, bottomBorder - 100, 32, 0.5f);

        spawn(Enemy.Type.BUBBLE, centerX, centerY, 32);
        spawnGroupInCircle(Enemy.Type.KAMIKAZE, 32, centerX, centerY, 150, 35, 0);
        spawnGroupInCircle(Enemy.Type.KAMIKAZE, 32, centerX, centerY, 150, 38, 0);
        spawnGroupInCircle(Enemy.Type.KAMIKAZE, 32, centerX, centerY, 150, 41, 0);

         */
    }
}
