package com.restaurantsapp.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.restaurantsapp.R;
import com.restaurantsapp.fragment.LoginFragment;

/**
 * Created by Admin on 26-03-2016.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        replaceFragment(new LoginFragment());
    }

    public void replaceFragment(final Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName()).commit();
    }

    public void addFragment(final Fragment newFragment, final Fragment hideFragment) {
        getFragmentManager().beginTransaction().add(R.id.container, newFragment, newFragment.getClass().getSimpleName())
                .hide(hideFragment).addToBackStack(newFragment.getClass().getSimpleName()).commit();
    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
