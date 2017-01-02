package com.head_first.aashi.experiments.bottom_navigation_bar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.head_first.aashi.experiments.utils.FragmentLauncher;
import com.head_first.aashi.experiments.R;

/**
 * Created by Aashish Indorewala on 24-Dec-16.
 */

public class BottomNavigationBarActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_bar_activity_bottom_navigation_bar);
        mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationBar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                launchSelectedMenuFragment(item);
                return true;
            }
        });
        mBottomNavigationView.inflateMenu(R.menu.bottom_navigation_bar_menu_items);
        launchSelectedMenuFragment(mBottomNavigationView.getMenu().findItem(R.id.menuItem1));
        //mBottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
    }

    private void launchSelectedMenuFragment(MenuItem item){
        Fragment menuItemFragment = new Fragment();
        switch(item.getItemId()){
            case R.id.menuItem1:
                menuItemFragment = new MenuItem1Fragmet();
                break;
            case R.id.menuItem2:
                menuItemFragment = new MenuItem2Fragment();
                break;
            case R.id.menuItem3:
                menuItemFragment = new MenuItem3Fragment();
                break;
            default:

        }
        FragmentLauncher.replaceFragmentInActivity(this.getSupportFragmentManager(), R.id.fragmentContainer, menuItemFragment);

    }
}
