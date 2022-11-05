package com.xxmassdeveloper.mpchartexample.notimportant;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.xxmassdeveloper.mpchartexample.R;

public class FlexmonSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.flexmon_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 1500);
    }

    private class splashhandler implements Runnable{
        public void run(){

            startActivity(new Intent(getApplication(), FlexmonMain.class));

            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            FlexmonSplash.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
    }

}