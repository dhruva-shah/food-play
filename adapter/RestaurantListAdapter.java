package com.restaurantsapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.restaurantsapp.R;
import com.restaurantsapp.RestaurantsApp;
import com.restaurantsapp.model.RestaurantModel;
import com.restaurantsapp.webservice.WSConstant;

import java.util.ArrayList;

public class RestaurantListAdapter extends BaseAdapter {

    private ArrayList<RestaurantModel> restaurantAdapterModel;
    private LayoutInflater layoutInflater;
    private Context context;
    private RestaurantsApp restaurantsApp;

    public RestaurantListAdapter(Context context, ArrayList<RestaurantModel> restaurantAdapterModel) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.restaurantAdapterModel = restaurantAdapterModel;
        this.context = context;
        restaurantsApp = (RestaurantsApp) context.getApplicationContext();
    }

    @Override
    public int getCount() {
        return restaurantAdapterModel.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantAdapterModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_restaurant_list, null);
            viewHolder = new ViewHolder();
            viewHolder.rest_list_row_img = (ImageView) convertView.findViewById(R.id.rest_list_row_imgview);
            viewHolder.rest_list_row_text = (TextView) convertView.findViewById(R.id.rest_list_row_textview);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final RestaurantModel restaurantModel = restaurantAdapterModel.get(position);
        viewHolder.rest_list_row_text.setText(restaurantModel.getRest_name());
        final String bannerImage = WSConstant.BASE_URL_BANNER + restaurantModel.getRest_banner1();

        restaurantsApp.getImageLoader().displayImage(bannerImage, viewHolder.rest_list_row_img, restaurantsApp.getDisplayImageOptions());
        return convertView;
    }

    private class ViewHolder {
        private ImageView rest_list_row_img;
        private TextView rest_list_row_text;

    }


}


