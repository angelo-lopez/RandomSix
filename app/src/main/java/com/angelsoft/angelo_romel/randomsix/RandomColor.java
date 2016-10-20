package com.angelsoft.angelo_romel.randomsix;

import android.content.Context;

import java.security.SecureRandom;


public class RandomColor {
    public RandomColor() {}

    //Returns a random generated color from the array-string in the color_collection.xml file.
    public String getRandomColor(Context c) {
        String[] randomColor = c.getResources().getStringArray(R.array.color_collection);
        //SecureRandom randomNum = new SecureRandom();
        return randomColor[new SecureRandom().nextInt(randomColor.length - 1)];
    }
}
