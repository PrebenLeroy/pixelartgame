package com.example.pixelart.models;

import android.graphics.Color;

/**
 * Created by prebe on 21/01/2018.
 */

public class Pixel {
    private int backgroundColor = Color.parseColor("#d3d3d3");

    public Pixel(){}

    public Pixel(int backgroundColor){
        this.backgroundColor = backgroundColor;
    }

    public void changeColor(int color){
        this.backgroundColor = color;
    }

    public int getBackgroundColor(){
        return this.backgroundColor;
    }
}
