package com.wielabs.mama.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.wielabs.mama.PlaceCallActivity;
import com.wielabs.mama.R;

public class Home extends Activity {

    BottomAppBar bottomAppBar;
    private int MIN_DISTANCE = 100;
    int i=0;
    float x1,x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        blink();

        bottomAppBar = findViewById(R.id.bottom_appbar);
        bottomAppBar.replaceMenu(R.menu.my_menu);

        findViewById(R.id.others_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, OtherActivity.class));
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Profile.class));
            }
        });

    }

    public void gotoPlaceCall(){
        startActivity(new Intent(this, PlaceCallActivity.class));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        Toast.makeText(this, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                    }

                    // Right to left swipe action
                    else
                    {
                        Toast.makeText(this, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                    }

                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {

    }

    private void blink(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 2000;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        ImageView txt = (ImageView) findViewById(R.id.imageLeft);
                        ImageView txt2 = (ImageView) findViewById(R.id.imageRight);
                        i++;

                        if(i == 3) {
                            Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
                            txt.startAnimation(fadeInAnimation);
                            txt.setVisibility(View.INVISIBLE);
                            txt2.startAnimation(fadeInAnimation);
                            txt2.setVisibility(View.INVISIBLE);
                            return;
                        }



                        if(txt.getVisibility() == View.VISIBLE){
                            Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
                            txt.startAnimation(fadeInAnimation);
                            txt.setVisibility(View.INVISIBLE);
                            txt2.startAnimation(fadeInAnimation);
                            txt2.setVisibility(View.INVISIBLE);
                        }else{
                            Animation fadeOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                            txt.startAnimation(fadeOutAnimation);
                            txt.setVisibility(View.VISIBLE);
                            txt2.startAnimation(fadeOutAnimation);
                            txt2.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }

                });
            }

        }).start();

    }
}
