package com.davidfabio.ui;

import com.davidfabio.game.Player;
import com.davidfabio.game.Score;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class UserInterface extends Group {
    private Label playerScore;
    private ProgressBar playerHealth;

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

        // Player Health bar
        this.playerHealth = new ProgressBar(0,player.getInitialHealth(),0.5f,false, UIBuilder.getSkin());
        this.playerHealth.setHeight(20f);
        this.playerHealth.setWidth(204f);
        this.playerHealth.setPosition((Gdx.graphics.getWidth() / 2) - (this.playerHealth.getWidth() / 2),Gdx.graphics.getHeight() * 0.1f);
        this.addActor(this.playerHealth);
    }

    public void update(Player player, Score score) {
        this.playerHealth.setValue(player.getHealth());
        this.playerScore.setText(getScoreText(score));
    }

    private String getScoreText(Score score) {
        return "Score: " + score.getPoints();
    }
}
