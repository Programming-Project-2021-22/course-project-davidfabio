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

public class UserInterface extends Group {
    private Label playerScore;
    private HorizontalGroup healthContainer;
    private ArrayList<Boolean> playerLives;

    public UserInterface() {
        super();
        this.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        UIBuilder.loadSkin();
    }

    public void init(Player player, Score score) {
        // Player Score
        this.playerScore = new Label(getScoreText(score), UIBuilder.getSkin());
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = UIBuilder.getSkin().getFont("font-title");
        this.playerScore.setStyle(labelStyle);
        this.playerScore.setFontScale(0.75f);
        this.playerScore.setHeight(30f);
        this.playerScore.setWidth(Gdx.graphics.getWidth() * 0.5f);
        this.playerScore.setPosition(30f,(Gdx.graphics.getHeight() * 0.9f));
        this.addActor(this.playerScore);

        this.playerLives = new ArrayList<>();
        for(int i = 0; i < player.getHealth(); i++) {
            this.playerLives.add(true);
        }

        // Player Health bar
        this.healthContainer = new HorizontalGroup();
        this.healthContainer.setHeight(20f);
        this.healthContainer.setWidth(204f);
        this.healthContainer.setPosition((Gdx.graphics.getWidth() / 2) - (this.healthContainer.getWidth() / 2),Gdx.graphics.getHeight() * 0.1f);
        updateHealthContainer(player.getHealth());
        this.addActor(this.healthContainer);
    }

    public void update(Player player, Score score) {
        this.playerScore.setText(getScoreText(score));
        updateHealthContainer(player.getHealth());
    }

    private String getScoreText(Score score) {
        return "Score: " + score.getPoints();
    }

    private void updateHealthContainer(int currentHealth) {
        for(int i = 0; i < this.playerLives.size(); i++) {
            this.playerLives.set(i, i < currentHealth);
        }

        Pixmap pixmapGold = new Pixmap(30, 30, Pixmap.Format.RGBA8888);
        pixmapGold.setColor(Color.GOLD);
        pixmapGold.fill();
        Pixmap pixmapDarkGrey = new Pixmap(30, 30, Pixmap.Format.RGBA8888);
        pixmapDarkGrey.setColor(Color.DARK_GRAY);
        pixmapDarkGrey.fill();

        this.healthContainer.clearChildren();
        for(boolean active : this.playerLives) {
            Image playerHealth;
            if (active)
                playerHealth = new Image(new Texture(pixmapGold));
            else
                playerHealth = new Image(new Texture(pixmapDarkGrey));
            playerHealth.setWidth(30f);
            playerHealth.setHeight(30f);
            playerHealth.setRotation(45f);
            this.healthContainer.addActor(playerHealth);
            this.healthContainer.center();
            this.healthContainer.padLeft(25);
            this.healthContainer.space(20);
        }
    }
}
