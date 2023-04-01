package com.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends ScreenAdapter {
    public static Player player;
    public static final int MAX_ENEMIES = 256;
    public static final int MAX_ENEMY_BULLETS = 256;
    public static ArrayList<Enemy> enemies;
    public static BulletEnemy[] enemyBullets;

    private static Random random;
    public static Random getRandom() { return random; }

    private ShapeRenderer shape;
    private static Camera camera;
    private static Level level;
    private static Stage stage;
    private static UserInterface userInterface;

    public static Camera getCamera() {
        return camera;
    }

    private static boolean isPaused = false;

    // for testing only
    private static float timeElapsed = 0;
    public static float getTimeElapsed() { return timeElapsed; }

    public static float displayRefreshRate;

    @Override
    public void show() {
        this.random = new Random();

        this.shape = new ShapeRenderer();
        this.camera = new Camera();
        this.stage = new Stage();
        this.level = new Level();
        Sounds.loadSounds();

        this.player = new Player();
        this.player.init(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 16, 0, new Polarity(), 260, 20);
        this.userInterface = new UserInterface();
        this.userInterface.init(player);
        this.stage.addActor(this.userInterface);

        this.enemies = new ArrayList<>();
        //for (int i = 0; i < MAX_ENEMIES; i += 1)
        //	enemies.add(new Enemy());

        this.enemyBullets = new BulletEnemy[GameScreen.MAX_ENEMY_BULLETS];
        for (int i = 0; i < GameScreen.MAX_ENEMY_BULLETS; i += 1)
            this.enemyBullets[i] = new BulletEnemy();

        this.isPaused = false;

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {

        // TODO (David): frametimes are uneven when using deltaTime (with VSync enabled), so for now at least we are not using it
        //float deltaTime = Gdx.graphics.getDeltaTime();
        float deltaTime = 1.0f / displayRefreshRate;
        timeElapsed += deltaTime;


        // get user input
        Inputs.update();

        // pause/unpause
        if (Inputs.esc.getWasPressed())
            isPaused = !isPaused;

        // restart game
        // TODO (David): quick and dirty solution; e.g. all the sounds get reloaded again, which is unnecessary
        if (Inputs.tab.getWasPressed())
            show();

        if (isPaused)
            return;


        // ---------------- update game logic ----------------

        // Reposition camera on player
        this.camera.updateCameraPosition(deltaTime, this.player);
        this.shape.setProjectionMatrix(this.camera.combined);


        // FOR TESTING ONLY: enemy spawning
        int activeEnemyCount = 0;
        int maxEnemies = 10;
        for (Enemy enemy : enemies)
            if (enemy.getIsActive())
                activeEnemyCount += 1;

        if (activeEnemyCount < maxEnemies) {
            float randomX = (float) (Math.random() * Settings.windowWidth);
            float randomY = (float) (Math.random() * Settings.windowHeight);
            float minDistanceToPlayer = 240;
            while (player.getDistanceTo(randomX, randomY) < minDistanceToPlayer) {
                randomX = (float) (Math.random() * Settings.windowWidth);
                randomY = (float) (Math.random() * Settings.windowHeight);
            }


            // NOTE (David): here we are allocating memory to create an enemy; should ideally be avoided
            //int rand = random.nextInt(2);
            int rand = 0;
            switch (rand) {
                case 0: {
                    EnemyChaser enemyChaser = new EnemyChaser();
                    enemyChaser.init(randomX, randomY, 20, 0, new Polarity(), 70, 7);
                    enemies.add(enemyChaser);
                    break;
                }
                case 1: {
                    EnemyStatic enemyStatic = new EnemyStatic();
                    enemyStatic.init(randomX, randomY, 28, 0, new Polarity(), 70, 7);
                    enemies.add(enemyStatic);
                    break;
                }
            }
        }


        // update enemies
        for (Enemy enemy : enemies)
            enemy.update(deltaTime);

        // update enemy bullets
        for (int i = 0; i < MAX_ENEMY_BULLETS; i += 1)
            enemyBullets[i].update(deltaTime);


        player.update(deltaTime); // player bullets get updated here as well
        this.userInterface.update(this.player);
        if (this.player.getHealth() <= 0) {
            ((Duality)Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
        }


        // ---------------- rendering ----------------
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);
        this.level.render(this.shape);
        this.stage.draw();

        for (Enemy enemy : enemies)
            if (enemy.getIsActive())
                enemy.render(shape, enemy.getPolarity().getColor());

        for (int i = 0; i < MAX_ENEMY_BULLETS; i += 1) {
            if (enemyBullets[i].getIsActive())
                enemyBullets[i].render(shape, enemyBullets[i].getPolarity().getColor());
        }

        player.render(shape, player.getPolarity().getColor()); // player bullets get rendered here as well
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }
}
