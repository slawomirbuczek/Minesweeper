package com.minesweeper.game.labels;

import android.widget.ImageView;

import java.util.Locale;

public abstract class Label {

    private final ImageView image_first;
    private final ImageView image_second;
    private final ImageView image_third;


    public Label(ImageView image_first, ImageView image_second, ImageView image_third) {
        this.image_first = image_first;
        this.image_second = image_second;
        this.image_third = image_third;
    }


    protected void setLabel(int number) {
        String formatted = String.format(Locale.getDefault(), "%03d", number);

        image_first.setImageResource(LabelImages.getImageId(formatted.charAt(0)));
        image_second.setImageResource(LabelImages.getImageId(formatted.charAt(1)));
        image_third.setImageResource(LabelImages.getImageId(formatted.charAt(2)));
    }

    protected void resetLabel() {
        image_first.setImageResource(LabelImages.getImageId('0'));
        image_second.setImageResource(LabelImages.getImageId('0'));
        image_third.setImageResource(LabelImages.getImageId('0'));
    }
}
