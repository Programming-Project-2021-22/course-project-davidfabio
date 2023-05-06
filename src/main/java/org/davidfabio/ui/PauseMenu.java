package org.davidfabio.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.davidfabio.PolygonWars;
import org.davidfabio.game.Score;

import java.util.ArrayList;
import java.util.function.Consumer;

public class PauseMenu extends Group {
    private Table mainTable;
    private ArrayList<Score> scores;

    public PauseMenu() {
        super();
        setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        UIBuilder.loadSkin();
    }

    public void init(ArrayList<Score> scores, Runnable onResume) {
        this.scores = scores;

        Pixmap pixmapBackground = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapBackground.setColor(Color.DARK_GRAY);
        pixmapBackground.fill();

        mainTable = new Table(UIBuilder.getSkin());
        mainTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmapBackground))));
        mainTable.setWidth(500);
        mainTable.setHeight(500);
        mainTable.setPosition((Gdx.graphics.getWidth() / 2) - (mainTable.getWidth() / 2),Gdx.graphics.getHeight() / 2 - (mainTable.getHeight() / 2));

        UIBuilder.addTitleLabel(mainTable,"Paused",true);
        UIBuilder.addButton(mainTable,"Resume",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onResume.run();
            }
        });
        UIBuilder.addButton(mainTable, "Return to Main Menu", true, new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            ((PolygonWars) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(scores));
          }
        });
        UIBuilder.addButton(mainTable,"Quit game",true,new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        addActor(mainTable);
    }
}
