package org.davidfabio.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UIBuilder {
    private static Skin skin;

    public static void loadSkin() {
        if (skin == null) {
            skin = new Skin(Gdx.files.internal("assets/ui/shade/skin/uiskin.json"));
        }
    }

    public static Skin getSkin() {
        return skin;
    }

    private static void addActorToTable(Table table, Actor actor, float height, boolean newRow) {
        addActorToTable(table,actor,height,Gdx.graphics.getWidth()*0.4f,newRow);
    }

    private static void addActorToTable(Table table, Actor actor, float height, float width, boolean newRow) {
        if (newRow)
            table.row();
        table.add(actor).minWidth(width).height(height).padBottom(10).expandX();
    }

    public static void addTitleLabel(Table table, String text, boolean newRow) {
        Label label = new Label(text, skin);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = UIBuilder.skin.getFont("font-title");
        label.setStyle(style);
        addActorToTable(table,label,60f,newRow);
    }

    public static void addSubtitleLabel(Table table, String text, float width, boolean newRow) {
        Label label = new Label(text, skin);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = UIBuilder.skin.getFont("font-title");
        label.setStyle(style);
        label.setFontScale(0.75f);
        addActorToTable(table,label,40f,width,newRow);
    }

    public static void addSubtitleLabel(Table table, String text, boolean newRow) {
        Label label = new Label(text, skin);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = UIBuilder.skin.getFont("font-title");
        label.setStyle(style);
        label.setFontScale(0.75f);
        addActorToTable(table,label,30f,newRow);
    }

    public static void addLabel(Table table, String text, float width, boolean newRow) {
        Label label = new Label(text, skin);
        addActorToTable(table,label,30f,width,newRow);
    }

    public static void addLabel(Table table, String text, boolean newRow) {
        Label label = new Label(text, skin);
        addActorToTable(table,label,30f,newRow);
    }

    public static void addButton(Table table, String name, boolean newRow, ClickListener listener) {
        TextButton button = new TextButton(name, skin);
        button.addListener(listener);
        addActorToTable(table,button,60f,newRow);
    }

    public static void addCheckBox(Table table, String name, boolean initialValue, float width, boolean newRow, ClickListener listener) {
        CheckBox checkBox = new CheckBox(name, skin);
        checkBox.setChecked(initialValue);
        checkBox.addListener(listener);
        checkBox.left();
        addActorToTable(table,checkBox,30f,width,newRow);
    }

    public static void addCheckBox(Table table, String name, boolean initialValue, boolean newRow, ClickListener listener) {
        CheckBox checkBox = new CheckBox(name, skin);
        checkBox.setChecked(initialValue);
        checkBox.addListener(listener);
        checkBox.left();
        addActorToTable(table,checkBox,30f,newRow);
    }

    public static void addSlider(Table table, float minimum, float maximum, float step, float initialValue, boolean newRow, ChangeListener listener) {
        Slider slider = new Slider(minimum, maximum, step, false, skin);
        slider.setValue(initialValue);
        slider.addListener(listener);
        addActorToTable(table,slider,30f,newRow);
    }

    public static void addTextInput(Table table, String defaultValue, float width, boolean newRow, ChangeListener listener) {
        TextField textField = new TextField(defaultValue, skin);
        textField.addListener(listener);
        addActorToTable(table,textField,30f,width,newRow);
    }

    public static void addTextInput(Table table, String defaultValue, boolean newRow, ChangeListener listener) {
        TextField textField = new TextField(defaultValue, skin);
        textField.addListener(listener);
        addActorToTable(table,textField,30f,newRow);
    }
}
