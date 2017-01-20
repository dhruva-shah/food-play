package com.restaurantsapp.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.restaurantsapp.R;
import com.restaurantsapp.RestaurantsApp;
import com.restaurantsapp.adapter.RestaurantListAdapter;
import com.restaurantsapp.fragment.AddRestaurantFragment;
import com.restaurantsapp.fragment.EditProfileFragment;
import com.restaurantsapp.fragment.OrderFragment;
import com.restaurantsapp.fragment.RestaurantListFragment;
import com.restaurantsapp.model.RestaurantModel;
import com.restaurantsapp.utils.Constants;
import com.restaurantsapp.utils.PreferenceUtils;
import com.restaurantsapp.utils.RoundedImageView;
import com.restaurantsapp.utils.Utils;
import com.restaurantsapp.webservice.WSLogout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private int previousSelectedMenuId = 0;
    private NavigationView mNavigationView;
    private RoundedImageView roundedImageView;
    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        roundedImageView = (RoundedImageView) findViewById(R.id.menu_header_user_profile);
        textViewName = (TextView) findViewById(R.id.menu_header_username);
        replaceFragment(new RestaurantListFragment());
        final PreferenceUtils preferenceUtils = new PreferenceUtils(this);
        final String userType = preferenceUtils.getString(PreferenceUtils.USER_TYPE);
        if (!TextUtils.isEmpty(userType)) {
            if (userType.equalsIgnoreCase(Constants.USER_TYPE_USER)) {
                mNavigationView.inflateMenu(R.menu.menu_user);
                setupDrawerContent(mNavigationView);
            } else {
                mNavigationView.inflateMenu(R.menu.menu_rm);
                setupDrawerContent(mNavigationView);
            }
        }
        updateProfileImageName();
    }


    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() != previousSelectedMenuId) {
                            previousSelectedMenuId = menuItem.getItemId();
                            if (menuItem.isChecked()) {
                                menuItem.setChecked(false);
                            } else {
                                menuItem.setChecked(true);
                            }
                            setNavigation(previousSelectedMenuId);
                        } else {
                            toggleDrawer();
                        }
                        return true;
                    }
                });
    }


    public void setTopBar(final String title, final boolean isBackButton) {
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
        if (isBackButton) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

    }


    public void setNoLoginTopBar(final String title) {
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setDisplayShowHomeEnabled(true);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    public void replaceFragment(final Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName()).commit();
    }


    public void addFragment(final Fragment newFragment, final Fragment hideFragment) {
        getFragmentManager().beginTransaction().add(R.id.container, newFragment, newFragment.getClass().getSimpleName()).hide(hideFragment).addToBackStack(newFragment.getClass().getSimpleName()).commit();
    }

    private void setNavigation(final int menuItem) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeNavDrawer();
            }
        }, 50);

        switch (menuItem) {
            case R.id.menu_home:
                replaceFragment(new RestaurantListFragment());
                break;
            case R.id.menu_edit_profile:
                replaceFragment(new EditProfileFragment());
                break;
            case R.id.menu_bookings:
                replaceFragment(new OrderFragment());
                break;
            case R.id.menu_add_restaurants:
                replaceFragment(new AddRestaurantFragment());
                break;
            case R.id.menu_logout:
                if (Utils.isOnline(this)) {
                    final AsyncLogout asyncLogout = new AsyncLogout();
                    asyncLogout.execute();
                } else {
                    Utils.displayNoInternetDialog(this);
                }
                break;

        }


    }

    private void toggleDrawer() {
        try {
            Utils.hideSoftKeyboard(MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            openNavDrawer();
        }
    }


    public void loadDrawerFragment(Integer integer, int position) {
        previousSelectedMenuId = integer;
        setNavigation(previousSelectedMenuId);
        mNavigationView.getMenu().getItem(position).setChecked(true);

    }

    protected boolean isNavDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        Utils.hideSoftKeyboard(MainActivity.this);
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    protected void openNavDrawer() {
        Utils.hideSoftKeyboard(MainActivity.this);
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    Utils.hideSoftKeyboard(MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
//                    isDrawerVisible();
                    getFragmentManager().popBackStackImmediate();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    public void isDrawerVisible() {
//        if (lockSmithApp != null) {
//            final PreferenceUtils preferenceUtils = lockSmithApp.getPreference();
//            if (preferenceUtils != null) {
//                final String userId = preferenceUtils.getUserId();
//                final boolean isDrawerVisible = preferenceUtils.getDrawerVisible();
//                if (!TextUtils.isEmpty(userId) && isDrawerVisible) {
//                    setTopBar(getString(R.string.home), true);
//                    mNavigationView.inflateMenu(R.menu.menu_navigation_user);
//                    setupDrawerContent(mNavigationView);
//                    updateProfileImageName();
//                    preferenceUtils.setIsDrawerVisible(false);
//                }
//            }
//        }
//
//    }

    @Override
    public void onBackPressed() {
        try {
            Utils.hideSoftKeyboard(MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }

        }
    }

    public void updateProfileImageName() {
        if (roundedImageView != null && textViewName != null) {
            final PreferenceUtils preferenceUtils = new PreferenceUtils(this);
            if (preferenceUtils != null) {
                final RestaurantsApp restaurantsApp = (RestaurantsApp) getApplicationContext();
                final String url = preferenceUtils.getString(PreferenceUtils.PROFILE_URL);
                final String name = preferenceUtils.getString(PreferenceUtils.USER_NAME);
                textViewName.setText(name);
                final String imageUrl = Constants.BASE_URL_PROFILE + url;
                if (!TextUtils.isEmpty(url)) {
                    restaurantsApp.getImageLoader().displayImage(imageUrl, roundedImageView, restaurantsApp.getDisplayImageOptions());
                } else {
                    restaurantsApp.getImageLoader().displayImage("", roundedImageView, restaurantsApp.getDisplayImageOptions());
                }
            }
        }
    }


    private class AsyncLogout extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        private WSLogout wsLogout;
        private Boolean isError;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utils.showProgressDialog(MainActivity.this, getString(R.string.loading), false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            wsLogout = new WSLogout(MainActivity.this);
            wsLogout.executeService();
            return null;

        }

        @Override
        protected void onPostExecute(Void login) {
            super.onPostExecute(login);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (wsLogout != null) {
                isError = wsLogout.isError();
                if (isError) {
                    final Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    final PreferenceUtils preferenceUtils = new PreferenceUtils(MainActivity.this);
                    preferenceUtils.clearAllPreferenceData();
                    startActivity(intent);
                    finish();
                } else if (wsLogout.isLogout()) {
                    Utils.displayLogoutAlertDialog(MainActivity.this, wsLogout.getMessage());
                } else {
                    Utils.displayAlertDialog(MainActivity.this, "", wsLogout.getMessage());
                }
            }
        }


    }


}
