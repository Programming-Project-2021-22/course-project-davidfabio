package com.davidfabio.game;

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
        UIFactory.loadSkin();
    }

    public void init(Player player) {
        // Player Score
        this.playerScore = new Label(getScoreText(GameScreen.getScore()), UIFactory.getSkin());
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = UIFactory.getSkin().getFont("font-title");
        this.playerScore.setStyle(style);
        this.playerScore.setFontScale(0.75f);
        this.playerScore.setHeight(30f);
        this.playerScore.setWidth(Gdx.graphics.getWidth() * 0.5f);
        this.playerScore.setPosition(30f,(Gdx.graphics.getHeight() * 0.9f));
        this.addActor(this.playerScore);

        // Player Health bar
        this.playerHealth = new ProgressBar(0,player.getInitialHealth(),0.5f,false, UIFactory.getSkin());
        this.playerHealth.setHeight(40f);
        this.playerHealth.setWidth(Gdx.graphics.getWidth() * 0.75f);
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
