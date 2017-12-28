package com.example.yinon.mycloset;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class README extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);
        View backgroundImage = findViewById(R.id.backgroung);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(30);
    }
}