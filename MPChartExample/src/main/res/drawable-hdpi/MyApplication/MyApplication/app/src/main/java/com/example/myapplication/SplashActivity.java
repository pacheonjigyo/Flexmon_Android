package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //이미지 뷰 넣기

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 1000);
    }

    private class splashhandler implements Runnable{
        public void run(){

            startActivity(new Intent(getApplication(), MainActivity.class));

            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
    }

}