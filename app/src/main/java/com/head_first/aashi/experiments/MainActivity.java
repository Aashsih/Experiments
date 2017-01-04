package com.head_first.aashi.experiments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.head_first.aashi.experiments.heart_sound_ui_experiments.controller.HeartSoundMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Navigate to another activity

        /*
        //Navigate to bottom_navigation_bar experiment
        Intent bottomNavigationBarActivityIntent = new Intent(this, BottomNavigationBarActivity.class);
        startActivity(bottomNavigationBarActivityIntent);
        */

        //Navigate to heart_sound_ui_experiments experiment
        Intent heartSoundMainActivityIntent = new Intent(this, HeartSoundMainActivity.class);
        startActivity(heartSoundMainActivityIntent);
    }
}
