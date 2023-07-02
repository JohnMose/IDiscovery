package com.example.idiscovery12;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Welcome extends AppCompatActivity {
    ImageView bgapp,cloverimg;
    LinearLayout splashtext,hometext,menus;
    Animation frombottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        frombottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
//images
        bgapp=(ImageView)findViewById(R.id.bgapp);
        cloverimg=(ImageView)findViewById(R.id.cloverimg);
//layout animations
        splashtext=(LinearLayout)findViewById(R.id.splashtext);
        hometext=(LinearLayout)findViewById(R.id.hometext);
        menus=(LinearLayout)findViewById(R.id.menus);
//the translation effects
        bgapp.animate().translationY(-1200).setDuration(900).setStartDelay(300);
        cloverimg.animate().alpha(0).setDuration(800).setStartDelay(600);
        splashtext.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);
//animation start
        hometext.startAnimation(frombottom);
        menus.startAnimation(frombottom);
//button
        Button btnEnter=findViewById(R.id.buttonEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {displayEnterDataScreen();}
        });
    }
    private void displayEnterDataScreen() {startActivity(new Intent(this,MainActivity.class));
    }

}