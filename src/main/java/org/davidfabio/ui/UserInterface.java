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
    /**
     * This label is used to show the Players Points during a game.
     */
    private Label playerScore;
    /**
     * This label is used to show the current Multiplier of a Player.
     */
    private Label scoreMultiplier;
    /**
     * This Group is used to track the lives of a Player.
     */
    private HorizontalGroup healthContainer;
    /**
     * This List contains the lives that a player has. True indicates an existing life, while False means that this live
     * was already lost.
     */
    private ArrayList<Boolean> playerLives;

    /**
     * This instances a new User Interface Object. It also loads the Default skin from the {@link UIBuilder} class if it was
     * not yet loaded.
     */
    public UserInterface() {
        super();
        setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        UIBuilder.loadSkin();
    }

    /**
     * Initializes the User Interface with the current information stored in the {@link Player} and {@link Score} classes.
     * In here we initialize the various UI components and give them the current values (Points, Lives, and more...).
     * The {@link UserInterface#playerLives} are initialized with the maximum number of health points a player may have.
     *
     * @param player the player that is currently playing a game
     * @param score the score of the currently played game
     */
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

        // Score Multiplier
        scoreMultiplier = new Label(getMultiplierText(player.getMultiplier()), UIBuilder.getSkin());
        scoreMultiplier.setStyle(labelStyle);
        scoreMultiplier.setFontScale(1.25f);
        scoreMultiplier.setHeight(60f);
        scoreMultiplier.setWidth(Gdx.graphics.getWidth() * 0.5f);
        scoreMultiplier.setPosition(Gdx.graphics.getWidth() - 90f,(Gdx.graphics.getHeight() * 0.9f));
        addActor(scoreMultiplier);

        // Player Health Bar
        playerLives = new ArrayList<>();
        for(int i = 0; i < player.getHealth(); i++) {
            playerLives.add(true);
        }
        healthContainer = new HorizontalGroup();
        healthContainer.setHeight(20f);
        healthContainer.setWidth(204f);
        healthContainer.setPosition((Gdx.graphics.getWidth() / 2) - (healthContainer.getWidth() / 2),Gdx.graphics.getHeight() * 0.1f);
        updateHealthContainer(player.getHealth());
        addActor(healthContainer);
    }

    /**
     * This method updates the information displayed by the User Interface using the provided player and score objects.
     *
     * @param player player containing the current live and pickups
     * @param score score containing the current points
     */
    public void update(Player player, Score score) {
        playerScore.setText(getScoreText(score));
        scoreMultiplier.setText(getMultiplierText(player.getMultiplier()));
        updateHealthContainer(player.getHealth());
    }

    /**
     * This method formats the Score to a String that is displayed in the {@link UserInterface#playerScore} Label.
     *
     * @param score object to format
     * @return String representation of the passed object
     */
    private String getScoreText(Score score) {
        return "Score: " + score.getPoints();
    }

    /**
     * This method formats the Multiplier to a String that is displayed in the {@link UserInterface#scoreMultiplier} Label.
     *
     * @param multiplier to format
     * @return String representation of the passed multiplier
     */
    private String getMultiplierText(int multiplier) {
        return multiplier + "x";
    }

    /**
     * This method updates the {@link UserInterface#healthContainer} group to show the current state of the players lives.
     * Any active lives are drawn in {@link Color#GOLD}, while the inactive lives are drawn in a {@link Color#DARK_GRAY}.
     *
     * @param currentHealth the number of lives a player has left.
     */
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

        healthContainer.clearChildren();
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
