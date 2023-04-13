package org.davidfabio.ui;

import com.badlogic.gdx.Gdx;
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

    public static void addTitleLabel(Table table, String text) {
        Label label = new Label(text, skin);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = UIBuilder.skin.getFont("font-title");
        label.setStyle(style);
        table.add(label).width(Gdx.graphics.getWidth()*0.75f).height(60f).padBottom(10);
        table.row();
    }

    public static void addSubtitleLabel(Table table, String text) {
        Label label = new Label(text, skin);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = UIBuilder.skin.getFont("font-title");
        label.setStyle(style);
        label.setFontScale(0.75f);
        table.add(label).width(Gdx.graphics.getWidth()*0.75f).height(40f).padBottom(10);
        table.row();
    }

    public static void addLabel(Table table, String text) {
        Label label = new Label(text, skin);
        table.add(label).width(Gdx.graphics.getWidth()*0.75f).height(30f).padBottom(10);
        table.row();
    }

    public static void addButton(Table table, String name, ClickListener listener) {
        TextButton button = new TextButton(name, skin);
        button.addListener(listener);
        table.add(button).width(Gdx.graphics.getWidth()*0.75f).height(60f).padBottom(10);
        table.row();
    }

    public static void addCheckBox(Table table, String name, boolean initialValue, ClickListener listener) {
        CheckBox checkBox = new CheckBox(name, skin);
        checkBox.setChecked(initialValue);
        checkBox.addListener(listener);
        checkBox.left();
        table.add(checkBox).width(Gdx.graphics.getWidth()*0.75f).height(30f).padBottom(10);
        table.row();
    }

    public static void addSlider(Table table, float minimum, float maximum, float step, float initialValue, ChangeListener listener) {
        Slider slider = new Slider(minimum, maximum, step, false, skin);
        slider.setValue(initialValue);
        slider.addListener(listener);
        table.add(slider).width(Gdx.graphics.getWidth()*0.75f).height(30f).padBottom(10);
        table.row();
    }
}
