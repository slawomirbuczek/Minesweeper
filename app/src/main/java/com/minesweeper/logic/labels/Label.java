package com.minesweeper.logic.labels;

import android.widget.ImageView;

import com.minesweeper.R;

abstract class Label {
    private ImageView imageX;
    private ImageView imageY;
    private ImageView imageZ;
    int number;

    Label(ImageView imageX, ImageView imageY, ImageView imageZ) {
        this.imageX = imageX;
        this.imageY = imageY;
        this.imageZ = imageZ;
        resetLabel();
    }

    void set(int number){
        String string = String.valueOf(number);
        if(string.length() == 1){
            changeImage(imageX, '0');
            changeImage(imageY, '0');
            changeImage(imageZ, string.charAt(0));
        }
        else if(string.length() == 2){
            changeImage(imageX, '0');
            changeImage(imageY, string.charAt(0));
            changeImage(imageZ, string.charAt(1));
        }else{
            changeImage(imageX, string.charAt(0));
            changeImage(imageY, string.charAt(1));
            changeImage(imageZ, string.charAt(2));
        }
    }

    void resetLabel(){
        imageX.setImageResource(R.drawable.d0);
        imageY.setImageResource(R.drawable.d0);
        imageZ.setImageResource(R.drawable.d0);
    }


    private void changeImage(ImageView image, char num){
        switch (num){
            case '0':
                image.setImageResource(R.drawable.d0);
                break;
            case '1':
                image.setImageResource(R.drawable.d1);
                break;
            case '2':
                image.setImageResource(R.drawable.d2);
                break;
            case '3':
                image.setImageResource(R.drawable.d3);
                break;
            case '4':
                image.setImageResource(R.drawable.d4);
                break;
            case '5':
                image.setImageResource(R.drawable.d5);
                break;
            case '6':
                image.setImageResource(R.drawable.d6);
                break;
            case '7':
                image.setImageResource(R.drawable.d7);
                break;
            case '8':
                image.setImageResource(R.drawable.d8);
                break;
            case '9':
                image.setImageResource(R.drawable.d9);
                break;
        }
    }
}
