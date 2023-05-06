package org.davidfabio.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.davidfabio.game.Player;
import org.davidfabio.game.Score;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

/**
 * This class handles the User Interface displayed while playing.
 * It contains the Health Bar, Score and Multiplier.
 */
public class UserInterface extends Group {
    private Label playerScore;
    private Label scoreMultiplier;
    private HorizontalGroup healthContainer;
    private ArrayList<Boolean> playerLives;

    public UserInterface() {
        super();
        this.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        UIBuilder.loadSkin();
    }

    public void init(Player player, Score score) {
        // Player Score
        playerScore = new Label(getScoreText(score), UIBuilder.getSkin());
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = UIBuilder.getSkin().getFont("font-title");
        playerScore.setStyle(labelStyle);
        playerScore.setFontScale(0.75f);
        playerScore.setHeight(30f);
        playerScore.setWidth(Gdx.graphics.getWidth() * 0.5f);
        playerScore.setPosition(30f,(Gdx.graphics.getHeight() * 0.9f));
        addActor(playerScore);

        scoreMultiplier = new Label(getMultiplierText(player.getMultiplier()), UIBuilder.getSkin());
        scoreMultiplier.setStyle(labelStyle);
        scoreMultiplier.setFontScale(1.25f);
        scoreMultiplier.setHeight(60f);
        scoreMultiplier.setWidth(Gdx.graphics.getWidth() * 0.5f);
        scoreMultiplier.setPosition(Gdx.graphics.getWidth() - 90f,(Gdx.graphics.getHeight() * 0.9f));
        addActor(scoreMultiplier);

        playerLives = new ArrayList<>();
        for(int i = 0; i < player.getHealth(); i++) {
            playerLives.add(true);
        }

        // Player Health bar
        healthContainer = new HorizontalGroup();
        healthContainer.setHeight(20f);
        healthContainer.setWidth(204f);
        healthContainer.setPosition((Gdx.graphics.getWidth() / 2) - (healthContainer.getWidth() / 2),Gdx.graphics.getHeight() * 0.1f);
        updateHealthContainer(player.getHealth());
        addActor(healthContainer);
    }

    public void update(Player player, Score score) {
        playerScore.setText(getScoreText(score));
        scoreMultiplier.setText(getMultiplierText(player.getMultiplier()));
        updateHealthContainer(player.getHealth());
    }

    private String getScoreText(Score score) {
        return "Score: " + score.getPoints();
    }

    private String getMultiplierText(int multiplier) {
        return multiplier + "x";
    }

    private void updateHealthContainer(int currentHealth) {
        for(int i = 0; i < playerLives.size(); i++) {
            playerLives.set(i, i < currentHealth);
        }

        Pixmap pixmapGold = new Pixmap(30, 30, Pixmap.Format.RGBA8888);
        pixmapGold.setColor(Color.GOLD);
        pixmapGold.fill();
        Pixmap pixmapDarkGrey = new Pixmap(30, 30, Pixmap.Format.RGBA8888);
        pixmapDarkGrey.setColor(Color.DARK_GRAY);
        pixmapDarkGrey.fill();

        this.healthContainer.clearChildren();
        for(boolean active : playerLives) {
            Image playerHealth;
            if (active)
                playerHealth = new Image(new Texture(pixmapGold));
            else
                playerHealth = new Image(new Texture(pixmapDarkGrey));
            playerHealth.setWidth(30f);
            playerHealth.setHeight(30f);
            playerHealth.setRotation(45f);
            healthContainer.addActor(playerHealth);
            healthContainer.center();
            healthContainer.padLeft(25);
            healthContainer.space(20);
        }
    }
}
