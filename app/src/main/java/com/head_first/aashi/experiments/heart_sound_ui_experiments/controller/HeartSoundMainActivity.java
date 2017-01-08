package com.head_first.aashi.experiments.heart_sound_ui_experiments.controller;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.head_first.aashi.experiments.R;
import com.wonderkiln.blurkit.BlurKit;

public class HeartSoundMainActivity extends AppCompatActivity {

    private static final int DEFAULT_MENU_ITEM = R.id.myPatients;

    //Views
    private Fragment menuItemFragment;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_sound_ui_experiments_activity_heart_sound_main);
        mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationBar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                launchSelectedMenuFragment(item);
                return true;
            }
        });
        mBottomNavigationView.inflateMenu(R.menu.heart_sound_ui_experiments_bottom_navigation_bar_menu_items);
        mBottomNavigationView.getMenu().findItem(DEFAULT_MENU_ITEM).setChecked(true);

        launchSelectedMenuFragment(mBottomNavigationView.getMenu().findItem(DEFAULT_MENU_ITEM));
    }

    private void launchSelectedMenuFragment(MenuItem item){
        Fragment menuItemFragment;
        switch(item.getItemId()){
            case R.id.userProfile:
                menuItemFragment = new UserProfileFragment();
                break;
            case R.id.myPatients:
                menuItemFragment = new Patients();
                ((Patients) menuItemFragment).setMyPatientClicked(true);
                break;
            case R.id.sharedPatients:
                menuItemFragment = new Patients();
                ((Patients) menuItemFragment).setMyPatientClicked(false);
                break;

            default:
                menuItemFragment = new Patients();
                ((Patients) menuItemFragment).setMyPatientClicked(true);
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, menuItemFragment)
                .addToBackStack(null)
                .commit();
    }


}
