package com.kithay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    TextView tv;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv=(TextView) findViewById(R.id.txtKithay);
        img=(ImageView) findViewById(R.id.imgIcon);

        Animation anim= AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(anim);
        img.startAnimation(anim);

        final Intent intent=new Intent(this,Signin.class);
        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
