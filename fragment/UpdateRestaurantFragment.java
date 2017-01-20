package com.restaurantsapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurantsapp.R;
import com.restaurantsapp.activity.MainActivity;
import com.restaurantsapp.adapter.BannerListAdapter;
import com.restaurantsapp.model.GalleryModel;
import com.restaurantsapp.model.RestaurantModel;
import com.restaurantsapp.utils.Utils;
import com.restaurantsapp.webservice.WSUpdateRestaurantNew;

import java.util.ArrayList;

/**
 * Created by indianic on 12/03/16.
 */
public class UpdateRestaurantFragment extends MainFragment implements View.OnClickListener {
    private static final int RQS_OPEN = 1;
    private static final int BANNER = 1;
    private static final int MENU = 2;

    private TextView buttonAddBanner;
    private TextView buttonAddMenu;
    private EditText editTextNumberOfTable;
    private EditText editTextRestName;

    private ArrayList<GalleryModel> arrayListFileBanner;
    private ArrayList<GalleryModel> arrayListFileMenu;


    private RecyclerView recyclerViewBanner;
    private RecyclerView recyclerViewMenu;


    private RestaurantModel restaurantModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            restaurantModel = getArguments().getParcelable("restaurantSelected");
            if (restaurantModel != null) {
                final ArrayList<String> bannerImages = restaurantModel.getRestImageUrl();
                final ArrayList<String> menuImages = restaurantModel.getRestMenuUrl();
                arrayListFileMenu = new ArrayList<>();
                arrayListFileBanner = new ArrayList<>();
                for (int i = 0; i < bannerImages.size(); i++) {
                    final GalleryModel galleryModel = new GalleryModel();
                    galleryModel.setImagePath(bannerImages.get(i).toString().trim());
                    arrayListFileBanner.add(galleryModel);
                }
                for (int i = 0; i < menuImages.size(); i++) {
                    final GalleryModel galleryModel = new GalleryModel();
                    galleryModel.setImagePath(menuImages.get(i).toString().trim());
                    arrayListFileMenu.add(galleryModel);
                }

            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_rest, null);

    }

    public void initView(final View view) {

        editTextRestName = (EditText) view.findViewById(R.id.et_nameofrest);
        buttonAddBanner = (TextView) view.findViewById(R.id.add_banner);
        buttonAddMenu = (TextView) view.findViewById(R.id.add_menu);
        editTextNumberOfTable = (EditText) view.findViewById(R.id.et_tablenum);
        recyclerViewBanner = (RecyclerView) view.findViewById(R.id.rest_banner_grid);
        recyclerViewMenu = (RecyclerView) view.findViewById(R.id.rest_menu_grid);

        final LinearLayoutManager layoutManagerMenu
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager layoutManagerBanner
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMenu.setLayoutManager(layoutManagerMenu);
        recyclerViewBanner.setLayoutManager(layoutManagerBanner);

        if (restaurantModel != null) {
            editTextRestName.setText(restaurantModel.getRest_name());
            final BannerListAdapter bannerListAdapter = new BannerListAdapter(getActivity(), arrayListFileBanner);
            recyclerViewBanner.setAdapter(bannerListAdapter);
            final BannerListAdapter menuListAdapter = new BannerListAdapter(getActivity(), arrayListFileMenu);
            recyclerViewMenu.setAdapter(menuListAdapter);
        }


        final Button restbutton = (Button) view.findViewById(R.id.rest_button_add);
        restbutton.setText(getString(R.string.update));
        restbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editTextRestName.getText().toString().trim();
//                final String numoftables = editTextNumberOfTable.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter restaurant name", Toast.LENGTH_SHORT).show();
                } else {
                    if (Utils.isOnline(getActivity())) {
                        if (restaurantModel != null) {
                            final String restaurantId = "" + restaurantModel.getRest_id();
                            final AsyncAddRestaurant asyncAddRestaurant = new AsyncAddRestaurant();
                            asyncAddRestaurant.execute(name, restaurantId);
                        }
                    } else {
                        Utils.displayNoInternetDialog(getActivity());
                    }
                }
            }
        });

        buttonAddBanner.setOnClickListener(this);
        buttonAddMenu.setOnClickListener(this);


    }

    @Override
    public void initActionBar() {
        ((MainActivity) getActivity()).setTopBar(getString(R.string.update_restaurant), false);
    }

    @Override
    public void onClick(View v) {
        final CustomGalleryFragment customGalleryFragment = new CustomGalleryFragment();
        switch (v.getId()) {
            case R.id.add_banner:
                customGalleryFragment.setTargetFragment(this, BANNER);
                ((MainActivity) getActivity()).addFragment(customGalleryFragment, this);
                break;
            case R.id.add_menu:
                customGalleryFragment.setTargetFragment(this, MENU);
                ((MainActivity) getActivity()).addFragment(customGalleryFragment, this);
                break;
        }
    }

    private class AsyncAddRestaurant extends AsyncTask<String, Void, Void> {
        private ProgressDialog progressDialog;
        private WSUpdateRestaurantNew wsUpdateRestaurantNew;
        private Boolean isError;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utils.showProgressDialog(getActivity(), getString(R.string.loading), false);

        }

        @Override
        protected Void doInBackground(String... params) {
            wsUpdateRestaurantNew = new WSUpdateRestaurantNew(getActivity());
            wsUpdateRestaurantNew.executeService(params[0], params[1], arrayListFileBanner, arrayListFileMenu);
            return null;
        }

        @Override
        protected void onPostExecute(Void login) {
            super.onPostExecute(login);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (wsUpdateRestaurantNew != null) {
                isError = wsUpdateRestaurantNew.isError();
                if (isError) {
                    final Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else if (wsUpdateRestaurantNew.isLogout()) {
                    Utils.displayLogoutAlertDialog(getActivity(), wsUpdateRestaurantNew.getMessage());
                } else {
                    Utils.displayAlertDialog(getActivity(), "", wsUpdateRestaurantNew.getMessage());
                }
            }
        }
    }


    public void addPhoto(final int type, final ArrayList<GalleryModel> arrayList) {
        switch (type) {
            case BANNER:
                arrayListFileBanner.clear();
                arrayListFileBanner.addAll(arrayList);
                final BannerListAdapter bannerListAdapter = new BannerListAdapter(getActivity(), arrayListFileBanner);
                recyclerViewBanner.setAdapter(bannerListAdapter);
                break;
            case MENU:
                arrayListFileMenu.clear();
                arrayListFileMenu.addAll(arrayList);
                final BannerListAdapter menuListAdapter = new BannerListAdapter(getActivity(), arrayListFileMenu);
                recyclerViewMenu.setAdapter(menuListAdapter);
                break;
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









