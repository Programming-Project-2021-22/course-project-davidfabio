package com.davidfabio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UserInterface extends Group {
    private ProgressBar playerHealth;
    private TextureAtlas atlas;
    private Skin skin;

    public UserInterface() {
        super();
        this.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.skin = new Skin(Gdx.files.internal("assets/ui/shade/skin/uiskin.json"));
    }

    public void init(Player player) {
        this.playerHealth = new ProgressBar(0,player.getInitialHealth(),0.5f,false, this.skin);
        this.playerHealth.setHeight(30f);
        this.playerHealth.setWidth(Gdx.graphics.getWidth() * 0.75f);
        this.playerHealth.setPosition((Gdx.graphics.getWidth() / 2) - (this.playerHealth.getWidth() / 2),Gdx.graphics.getHeight() * 0.1f);
        this.addActor(this.playerHealth);
    }

    public void update(Player player) {
        this.playerHealth.setValue(player.getHealth());
    }
}
