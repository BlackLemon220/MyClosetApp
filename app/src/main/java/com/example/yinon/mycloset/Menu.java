package com.example.yinon.mycloset;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        View backgroundImage = findViewById(R.id.backgroung);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(30);
        Intent loginIntent = getIntent();
        username = loginIntent.getExtras().getString("username");
    }
}