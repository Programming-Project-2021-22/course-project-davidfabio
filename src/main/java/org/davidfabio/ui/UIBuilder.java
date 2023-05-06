package org.davidfabio.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * This class is used to instance various kinds of Labels, Buttons and other UI elements.
 * This is used to unify the screen building experience.
 */
public class UIBuilder {
    /**
     * This contains a {@link Skin} that is used throughout the Application to style every interface object.
     * This Variable is instanced using the {@link UIBuilder#loadSkin()} method.
     */
    private static Skin skin;

    /**
     * This method loads the File "uiskin.json" from the shade-skin. If the file was already loaded, this method does
     * nothing.
     * The skin is loaded into the Variable {@link UIBuilder#skin}.
     */
    public static void loadSkin() {
        if (skin == null) {
            skin = new Skin(Gdx.files.internal("src/main/resources/ui/shade/skin/uiskin.json"));
        }
    }

    /**
     * Returns the {@link Skin} which is used throughout the application.
     * @return the application's skin
     */
    public static Skin getSkin() {
        return skin;
    }

    /**
     * This method adds the passed actor to the passed table. It automatically assigns a default width.
     *
     * @param table where the {@param actor} is added to
     * @param actor the element to add to {@param table}.
     * @param height the height of the element
     * @param newRow if true, a new row is created. Otherwise {@param actor} is added to the current row.
     */
    private static void addActorToTable(Table table, Actor actor, float height, boolean newRow) {
        addActorToTable(table,actor,height,Gdx.graphics.getWidth()*0.4f,newRow);
    }

    /**
     * This method adds the passed actor to the passed table.
     *
     * @param table where the {@param actor} is added to
     * @param actor the element to add to {@param table}.
     * @param height the height of the element
     * @param width the width of the element
     * @param newRow if true, a new row is created. Otherwise {@param actor} is added to the current row.
     */
    private static void addActorToTable(Table table, Actor actor, float height, float width, boolean newRow) {
        if (newRow)
            table.row();
        table.add(actor).minWidth(width).height(height).padBottom(10).expandX();
    }

    /**
     * This method instances a new Label using the provided text. The Label is 60f high and uses the title font.
     *
     * @param table where the {@link Label} is added to
     * @param text text to use for the {@link Label}
     * @param newRow if true, a new row is created. Otherwise, the {@link Label} is added to the current row.
     */
    public static void addTitleLabel(Table table, String text, boolean newRow) {
        Label label = new Label(text, skin);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = UIBuilder.skin.getFont("font-title");
        label.setStyle(style);
        addActorToTable(table,label,60f,newRow);
    }

    /**
     * This method instances a new Label using the provided text. The Label is 40f high and uses the title font.
     *
     * @param table where the {@link Label} is added to
     * @param text text to use for the {@link Label}
     * @param width the width of the {@link Label}
     * @param newRow if true, a new row is created. Otherwise, the {@link Label} is added to the current row.
     */
    public static void addSubtitleLabel(Table table, String text, float width, boolean newRow) {
        Label label = new Label(text, skin);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = UIBuilder.skin.getFont("font-title");
        label.setStyle(style);
        label.setFontScale(0.75f);
        addActorToTable(table,label,40f,width,newRow);
    }

    /**
     * This method instances a new Label using the provided text. The Label is 30f high, scaled to 75%
     * and uses the title font.
     *
     * @param table where the {@link Label} is added to
     * @param text text to use for the {@link Label}
     * @param newRow if true, a new row is created. Otherwise, the {@link Label} is added to the current row.
     */
    public static void addSubtitleLabel(Table table, String text, boolean newRow) {
        Label label = new Label(text, skin);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = UIBuilder.skin.getFont("font-title");
        label.setStyle(style);
        label.setFontScale(0.75f);
        addActorToTable(table,label,30f,newRow);
    }

  /**
   * This method instances a new Label using the provided text. The Label is 30f high and uses the normal font.
   *
   * @param table where the {@link Label} is added to
   * @param text text to use for the {@link Label}
   * @param width the width of the {@link Label}
   * @param newRow if true, a new row is created. Otherwise, the {@link Label} is added to the current row.
   */
  public static void addLabel(Table table, String text, float width, boolean newRow) {
        Label label = new Label(text, skin);
        addActorToTable(table,label,30f,width,newRow);
    }

    /**
     * This method instances a new Label using the provided text. The Label is 30f high and uses the normal font.
     *
     * @param table where the {@link Label} is added to
     * @param text text to use for the {@link Label}
     * @param newRow if true, a new row is created. Otherwise, the {@link Label} is added to the current row.
     */
    public static void addLabel(Table table, String text, boolean newRow) {
        Label label = new Label(text, skin);
        addActorToTable(table,label,30f,newRow);
    }

    /**
     * This method instances a new {@link Button} using the provided name. The Button is 60f high.
     *
     * @param table where the {@link Button} is added to
     * @param name to use for the {@link Button}
     * @param newRow if true, a new row is created. Otherwise, the {@link Button} is added to the current row.
     * @param listener a listener that needs to be executed onClick.
     */
    public static void addButton(Table table, String name, boolean newRow, ClickListener listener) {
        TextButton button = new TextButton(name, skin);
        button.addListener(listener);
        addActorToTable(table,button,60f,newRow);
    }

    /**
     * This method instances a new {@link CheckBox} using the provided name. The CheckBox is 30f high.
     *
     * @param table where the {@link CheckBox} is added to
     * @param name to use for the {@link CheckBox}
     * @param initialValue the default value for the {@link CheckBox}
     * @param width the width for the {@link CheckBox}
     * @param newRow if true, a new row is created. Otherwise, the {@link CheckBox} is added to the current row.
     * @param listener a listener that needs to be executed onClick.
     */
    public static void addCheckBox(Table table, String name, boolean initialValue, float width, boolean newRow, ClickListener listener) {
        CheckBox checkBox = new CheckBox(name, skin);
        checkBox.setChecked(initialValue);
        checkBox.addListener(listener);
        checkBox.left();
        addActorToTable(table,checkBox,30f,width,newRow);
    }

    /**
     * This method instances a new {@link CheckBox} using the provided name. The CheckBox is 30f high.
     *
     * @param table where the {@link CheckBox} is added to
     * @param name to use for the {@link CheckBox}
     * @param initialValue the default value for the {@link CheckBox}
     * @param newRow if true, a new row is created. Otherwise, the {@link CheckBox} is added to the current row.
     * @param listener a listener that needs to be executed onClick.
     */
    public static void addCheckBox(Table table, String name, boolean initialValue, boolean newRow, ClickListener listener) {
        CheckBox checkBox = new CheckBox(name, skin);
        checkBox.setChecked(initialValue);
        checkBox.addListener(listener);
        checkBox.left();
        addActorToTable(table,checkBox,30f,newRow);
    }

    /**
     * This method instances a new {@link Slider} using the provided minimum and maximum. The Slider is 30f high.
     *
     * @param table where the {@link Slider} is added to.
     * @param minimum the minimum value the slider may reach.
     * @param maximum the maximum value the slider may reach.
     * @param step the slider may only move in these steps between minimum and maximum.
     * @param initialValue starting value for the slider.
     * @param newRow if true, a new row is created. Otherwise, the {@link Slider} is added to the current row.
     * @param listener a listener that needs to be executed onChange.
     */
    public static void addSlider(Table table, float minimum, float maximum, float step, float initialValue, boolean newRow, ChangeListener listener) {
        Slider slider = new Slider(minimum, maximum, step, false, skin);
        slider.setValue(initialValue);
        slider.addListener(listener);
        addActorToTable(table,slider,30f,newRow);
    }

    /**
     * This method instances a new {@link TextField} using the provided defaultValue. The TextField is 30f high.
     * @param table where the {@link TextField} is added to.
     * @param defaultValue the default Value to be used in the TextField
     * @param width the width for the {@link TextField}
     * @param newRow if true, a new row is created. Otherwise, the {@link TextField} is added to the current row.
     * @param listener a listener that needs to be executed onChange.
     */
    public static void addTextInput(Table table, String defaultValue, float width, boolean newRow, ChangeListener listener) {
        TextField textField = new TextField(defaultValue, skin);
        textField.addListener(listener);
        addActorToTable(table,textField,30f,width,newRow);
    }

    /**
     * This method instances a new {@link TextField} using the provided defaultValue. The TextField is 30f high.
     *
     * @param table where the {@link TextField} is added to.
     * @param defaultValue the default Value to be used in the TextField
     * @param newRow if true, a new row is created. Otherwise, the {@link TextField} is added to the current row.
     * @param listener a listener that needs to be executed onChange.
     */
    public static void addTextInput(Table table, String defaultValue, boolean newRow, ChangeListener listener) {
        TextField textField = new TextField(defaultValue, skin);
        textField.addListener(listener);
        addActorToTable(table,textField,30f,newRow);
    }

    /**
     * This method instances a new {@link SelectBox} using the provided selected and items values. The SelectBox is 30f high.
     *
     * @param table where the {@link SelectBox} is added to.
     * @param newRow if true, a new row is created. Otherwise, the {@link TextField} is added to the current row.
     * @param listener a listener that needs to be executed onChange.
     * @param selected the currently selected item
     * @param items the list of items that can be selected
     */
    public static void addSelectBox(Table table, boolean newRow, ChangeListener listener, Object selected, Object... items) {
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems(items);
        selectBox.setSelected(selected);
        selectBox.addListener(listener);
        addActorToTable(table,selectBox,30f,newRow);
    }
}
