package com.restaurantsapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.restaurantsapp.R;
import com.restaurantsapp.RestaurantsApp;
import com.restaurantsapp.activity.MainActivity;
import com.restaurantsapp.adapter.HorizontalListAdapter;
import com.restaurantsapp.model.RestaurantModel;
import com.restaurantsapp.utils.Constants;
import com.restaurantsapp.utils.PreferenceUtils;

/**
 * Created by indianic on 31/12/15.
 */
public class RestaurantFragment extends MainFragment {
    private RestaurantModel restaurantSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundle = this.getArguments();
        if (bundle != null) {
            restaurantSelected = bundle.getParcelable("restaurantSelected");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurants_details, null);
    }

    public void initView(final View view) {
        initActionBar();
        final RecyclerView gridView_restpic = (RecyclerView) view.findViewById(R.id.rest_photo_grid);
        final RecyclerView gridView_menupic = (RecyclerView) view.findViewById(R.id.rest_menu_grid);
        final Button buttonBookTable = (Button) view.findViewById(R.id.rest_button_book);
        final LinearLayoutManager layoutManagerMenu
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager layoutManagerBanner
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        gridView_menupic.setLayoutManager(layoutManagerMenu);
        gridView_restpic.setLayoutManager(layoutManagerBanner);

        final Button button_book = (Button) view.findViewById(R.id.rest_button_book);
        final PreferenceUtils preferenceUtils = new PreferenceUtils(getActivity());
        final String userType = preferenceUtils.getString(PreferenceUtils.USER_TYPE);
        if (!TextUtils.isEmpty(userType) && userType.equalsIgnoreCase(Constants.USER_TYPE_RM)) {
            button_book.setVisibility(View.INVISIBLE);
            button_book.setClickable(false);
        }

        final HorizontalListAdapter restImageAdapter = new HorizontalListAdapter(getActivity(), restaurantSelected.getRestImageUrl());
        gridView_restpic.setAdapter(restImageAdapter);

        final HorizontalListAdapter restMenuAdapter = new HorizontalListAdapter(getActivity(), restaurantSelected.getRestMenuUrl());
        gridView_menupic.setAdapter(restMenuAdapter);


        buttonBookTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restaurantSelected != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putParcelable("restaurantSelected", restaurantSelected);
                    final GameFragment gameFragment = new GameFragment();
                    gameFragment.setArguments(bundle);
                    ((MainActivity) getActivity()).addFragment(gameFragment, RestaurantFragment.this);
                }
            }
        });


    }

    @Override
    public void initActionBar() {
        if (restaurantSelected != null) {
            ((MainActivity) getActivity()).setTopBar(restaurantSelected.getRest_name(), false);
        }
    }
}



