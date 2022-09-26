package com.example.handicrafthorizon;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView img;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        img = findViewById(R.id.Splashimg);
        animation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        img.setAnimation(animation);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };myThread.start();
    }
}
