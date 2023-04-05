package com.davidfabio.game;

import com.badlogic.gdx.graphics.Color;

public class Polarity {
    public final static float OPPOSITE_POLARITY_MULTIPLIER = 2.0f;
    private Color color;

    /**
     * The Polarity Constructor randomly decides which color the polarity instantiates to.
     * If you want to specify the color to use, you can simply add the Color to the Polarity
     * Constructor as a parameter.
     *
     * @return      a random polarity from the available ones.
     */
    public Polarity() {
        if (Math.random() <= 0.5) {
            this.color = Settings.FIRST_COLOR;
        } else {
            this.color = Settings.SECOND_COLOR;
        }
    }

    public Polarity(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Polarity duplicate() {
        return new Polarity(this.color);
    }

    public void togglePolarity() {
        if (this.color == Settings.FIRST_COLOR) {
            this.color = Settings.SECOND_COLOR;
        } else {
            this.color = Settings.FIRST_COLOR;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Polarity) {
            return this.color.equals(((Polarity) obj).getColor());
        } else {
            return false;
        }
    }
}
