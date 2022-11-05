package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CharSequence counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        for(int i = 0; i < sectionsPagerAdapter.getCount(); i++)
        {
            counter = sectionsPagerAdapter.getPageTitle(i);

            if(counter.equals("친구"))
            {
                Log.e("simplechat: ", "친구");
            }
            else if(counter.equals("채팅"))
            {
                Log.e("simplechat: ", "채팅");

            }
            else if(counter.equals("커뮤니티"))
            {
                Log.e("simplechat: ", "커뮤니티");
            }
        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.menu.actionbar);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.logo));

//        CustomListFragment customListFrgmt = (CustomListFragment) getSupportFragmentManager().findFragmentById(R.id.customlistfragment);
//        customListFrgmt.addItem(ContextCompat.getDrawable(this, R.drawable.logo), "New Box", "New Account Box Black 36dp") ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_btn1:
                return true;
            case R.id.action_btn2:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}