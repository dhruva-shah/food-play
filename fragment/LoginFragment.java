package com.restaurantsapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurantsapp.R;
import com.restaurantsapp.activity.LoginActivity;
import com.restaurantsapp.activity.MainActivity;
import com.restaurantsapp.utils.Utils;
import com.restaurantsapp.webservice.WSLogin;

/**
 * Created by Admin on 26-03-2016.
 */
public class LoginFragment extends MainFragment {
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
            public void onClick(View v) {
                final String email = editTextEmail.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();
                if (Utils.isOnline(getActivity(), true)) {
                    if (isValid(email, password)) {
                        final AsyncLogin asyncLogin = new AsyncLogin();
                        asyncLogin.execute(email, password);
                    }
                }
            }
        });


        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity) getActivity()).addFragment(new RegisterFragment(), LoginFragment.this);
            }
        });

    }

    @Override
    public void initActionBar() {

    }


    private boolean isValid(final String email, final String password) {
        boolean isValid = false;
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
        } else if (!isEmailValid(email)) {
            Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            isValid = true;
        }
        return isValid;
    }


    private boolean isEmailValid(final String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class AsyncLogin extends AsyncTask<String, Void, Void> {
        private ProgressDialog progressDialog;
        private WSLogin wsLogin;
        private boolean isError;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utils.showProgressDialog(getActivity(), getString(R.string.loading), true);
        }

        @Override
        protected Void doInBackground(String... params) {
            wsLogin = new WSLogin(getActivity());
            wsLogin.executeService(params[0], params[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void login) {
            super.onPostExecute(login);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (wsLogin != null) {
                isError = wsLogin.isError();
                if (isError) {
                    final Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Utils.displayAlertDialog(getActivity(), "", wsLogin.getMessage());
                }
            }
        }
    }
}

