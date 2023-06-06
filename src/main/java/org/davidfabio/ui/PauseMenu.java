package org.davidfabio.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.davidfabio.PolygonWars;
import org.davidfabio.game.Score;

import java.util.ArrayList;

/**
 * This class is used to display a Pause Menu during the Game Loop.
 */
public class PauseMenu extends Group {
    /**
     * This is used as a basis for the Pause Menu Screen. It contains all controls.
     */
    private Table mainTable;

    /**
     * Creates a new PauseMenu object and loads the Skin using {@link UIBuilder#loadSkin()}.
     */
    public PauseMenu() {
        super();
        setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        UIBuilder.loadSkin();
    }

    /**
     * This method builds the PauseMenu screen. It adds all Buttons and Labels that are available to the user.
     *
     * @param scores all past game scores that need to be passed on to other screens.
     * @param onResume action to be executed in order to get back to the Game.
     */
    public void init(ArrayList<Score> scores, Runnable onResume) {
        Pixmap pixmapBackground = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapBackground.setColor(Color.DARK_GRAY);
        pixmapBackground.fill();

        mainTable = new Table(UIBuilder.getSkin());
        mainTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmapBackground))));
        mainTable.setWidth(Gdx.graphics.getWidth() * 0.65f);
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
