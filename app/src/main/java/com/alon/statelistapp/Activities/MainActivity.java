package com.alon.statelistapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;

import com.alon.statelistapp.Fragments.FirstFragment;
import com.alon.statelistapp.Fragments.SecondFragment;
import com.alon.statelistapp.Models.State;
import com.alon.statelistapp.R;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFirstFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.main_FL);

        if (fragment == null) // if it the first time to call the first fragment
        {
            fragment = new FirstFragment();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(R.id.main_FL, fragment, "0").commit();

        }
    }

    // replace the first fragment with the second fragment
    public void LoadSecFragment(State s) {

        SecondFragment secondFragment = new SecondFragment();

        getIntent().putExtra("StateOb", s);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FL, secondFragment, (getSupportFragmentManager().getBackStackEntryCount() - 1) + "").addToBackStack(null).commit();
    }

    @Override
    public void onFirstFragmentInteraction(Uri uri) {

    }
}