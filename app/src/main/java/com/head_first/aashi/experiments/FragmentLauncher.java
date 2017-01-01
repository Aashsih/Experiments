package com.head_first.aashi.experiments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aashish Indorewala on 01-Jan-17.
 */

public final class FragmentLauncher {

    public static void replaceFragmentInActivity(AppCompatActivity appCompatActivity, int containerId, Fragment fragment){
        //Create new fragment transaction
        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Replace whatever is in the fragment container
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.addToBackStack(null);

        //Commit the Transaction
        fragmentTransaction.commit();
    }

    public static void addFragmentToActivity(AppCompatActivity appCompatActivity, int id, Fragment fragment){
        //Create new fragment transaction
        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Replace whatever is in the fragment container
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.addToBackStack(null);

        //Commit the Transaction
        fragmentTransaction.commit();
    }

}
