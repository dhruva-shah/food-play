package com.restaurantsapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.restaurantsapp.R;
import com.restaurantsapp.activity.LoginActivity;

/**
 * Created by Admin on 26-03-2016.
 */
public class HomeFragment extends MainFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }

    @Override
    public void initView(View view) {
        final EditText editTextEmail = (EditText) view.findViewById(R.id.activity_login_edittext_email);
        final EditText editTextPassword = (EditText) view.findViewById(R.id.activity_login_edittext_password);
        final Button buttonLogin = (Button) view.findViewById(R.id.activity_login_button_login);
        final TextView textViewSignUp = (TextView) view.findViewById(R.id.activity_login_button_signup);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity) getActivity()).addFragment(new RegisterFragment(), HomeFragment.this);
            }
        });

    }

    @Override
    public void initActionBar() {

    }
}
