package com.head_first.aashi.experiments.heart_sound_ui_experiments;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.head_first.aashi.experiments.utils.FragmentLauncher;
import com.head_first.aashi.experiments.R;

public class HeartSoundMainActivity extends AppCompatActivity {

    private static final int DEFAULT_MENU_ITEM = R.id.myPatients;

    //Views
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
        //mBottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
    }

    private void launchSelectedMenuFragment(MenuItem item){
        Fragment menuItemFragment = new Fragment();
        switch(item.getItemId()){
            case R.id.userProfile:
                menuItemFragment = new UserProfileFragment();
                break;
            case R.id.myPatients:
                menuItemFragment = new MyPatientsFragment();
                break;
            case R.id.sharedPatients:
                menuItemFragment = new SharedPatientsFragment();
                break;
            default: menuItemFragment = new MyPatientsFragment();
                break;

        }
        FragmentLauncher.replaceFragmentInActivity(this.getSupportFragmentManager(), R.id.fragmentContainer, menuItemFragment);

    }
}
