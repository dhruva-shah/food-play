package com.restaurantsapp.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.restaurantsapp.R;
import com.restaurantsapp.activity.MainActivity;
import com.restaurantsapp.adapter.RestaurantListAdapter;
import com.restaurantsapp.model.RestaurantModel;
import com.restaurantsapp.utils.Constants;
import com.restaurantsapp.utils.PreferenceUtils;
import com.restaurantsapp.utils.Utils;
import com.restaurantsapp.webservice.WSGetRestaurants;

import java.util.ArrayList;

/**
 * Created by Admin on 26-03-2016.
 */
public class RestaurantListFragment extends MainFragment {

    private ListView restListListview;
    private RestaurantListAdapter restaurantListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurats, null);
    }

    @Override
    public void initView(View view) {
        initActionBar();
        restListListview = (ListView) view.findViewById(R.id.fragment_restaurats_listview);
        if (Utils.isOnline(getActivity())) {
            final AsyncRestList asyncRestList = new AsyncRestList();
            asyncRestList.execute();
        } else {
            Utils.displayNoInternetDialog(getActivity());
        }

        restListListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final RestaurantModel restaurantModel = (RestaurantModel) adapterView.getItemAtPosition(i);
                if (restaurantModel != null) {
                    final PreferenceUtils preferenceUtils = new PreferenceUtils(getActivity());
                    final String userType = preferenceUtils.getString(PreferenceUtils.USER_TYPE);
                    if (userType.equalsIgnoreCase(Constants.USER_TYPE_USER)) {
                        final Bundle bundle = new Bundle();
                        bundle.putParcelable("restaurantSelected", restaurantModel);
                        final RestaurantFragment restaurantFragment = new RestaurantFragment();
                        restaurantFragment.setArguments(bundle);
                        ((MainActivity) getActivity()).addFragment(restaurantFragment, RestaurantListFragment.this);
                    } else {
                        final Bundle bundle = new Bundle();
                        bundle.putParcelable("restaurantSelected", restaurantModel);
                        final UpdateRestaurantFragment restaurantFragment = new UpdateRestaurantFragment();
                        restaurantFragment.setArguments(bundle);
                        ((MainActivity) getActivity()).addFragment(restaurantFragment, RestaurantListFragment.this);
                    }


                }
            }
        });


    }

    @Override
    public void initActionBar() {
        ((MainActivity) getActivity()).setTopBar(getString(R.string.restaurantslist), true);
    }


    private class AsyncRestList extends AsyncTask<Void, Void, ArrayList<RestaurantModel>> {
        private ProgressDialog progressDialog;
        private WSGetRestaurants wsGetRestaurants;
        private Boolean isError;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utils.showProgressDialog(getActivity(), getString(R.string.loading), false);
        }

        @Override
        protected ArrayList<RestaurantModel> doInBackground(Void... params) {
            wsGetRestaurants = new WSGetRestaurants(getActivity());
            return wsGetRestaurants.executeService();

        }

        @Override
        protected void onPostExecute(ArrayList<RestaurantModel> login) {
            super.onPostExecute(login);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (wsGetRestaurants != null) {
                isError = wsGetRestaurants.isError();
                if (isError) {
                    restaurantListAdapter = new RestaurantListAdapter(getActivity(), login);
                    restListListview.setAdapter(restaurantListAdapter);
                } else if (wsGetRestaurants.isLogout()) {
                    Utils.displayLogoutAlertDialog(getActivity(), wsGetRestaurants.getMessage());
                } else {
                    Utils.displayAlertDialog(getActivity(), "", wsGetRestaurants.getMessage());
                }
            }
        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initActionBar();
        }
    }
}
