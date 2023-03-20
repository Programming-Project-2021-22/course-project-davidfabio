package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public class Polarity {
    private Color currentColor;

    public Polarity() {
        if (Math.random() <= 0.5) {
            this.currentColor = GameSettings.FIRST_COLOR;
        } else {
            this.currentColor = GameSettings.SECOND_COLOR;
        }
    }

    public Polarity(Color color) {
        this.currentColor = color;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public Polarity duplicate() {
        return new Polarity(this.currentColor);
    }

    public void togglePolarity() {
        if (this.currentColor == GameSettings.FIRST_COLOR) {
            this.currentColor = GameSettings.SECOND_COLOR;
        } else {
            this.currentColor = GameSettings.FIRST_COLOR;
        }
    }
}
