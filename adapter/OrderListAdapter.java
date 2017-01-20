package com.restaurantsapp.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restaurantsapp.R;
import com.restaurantsapp.RestaurantsApp;
import com.restaurantsapp.fragment.OrderFragment;
import com.restaurantsapp.model.OrderModel;
import com.restaurantsapp.model.RestaurantModel;
import com.restaurantsapp.utils.Constants;
import com.restaurantsapp.utils.PreferenceUtils;
import com.restaurantsapp.utils.Utils;

import java.util.ArrayList;

public class OrderListAdapter extends BaseAdapter {

    private ArrayList<OrderModel> restaurantAdapterModel;
    private LayoutInflater layoutInflater;
    private Context context;
    private RestaurantsApp restaurantsApp;
    private String userType;
    private OrderFragment orderFragment;

    public OrderListAdapter(OrderFragment fragment, ArrayList<OrderModel> restaurantAdapterModel) {
        this.restaurantAdapterModel = restaurantAdapterModel;
        this.context = fragment.getActivity();
        this.orderFragment = fragment;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        restaurantsApp = (RestaurantsApp) context.getApplicationContext();
        final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
        userType = preferenceUtils.getString(PreferenceUtils.USER_TYPE);
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
            convertView = layoutInflater.inflate(R.layout.row_order, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewRestaurantName = (TextView) convertView.findViewById(R.id.row_order_text_view_restaurant_name);
            viewHolder.textViewUserName = (TextView) convertView.findViewById(R.id.row_order_text_view_user_name);
            viewHolder.textViewDateTime = (TextView) convertView.findViewById(R.id.row_order_text_view_date_time);
            viewHolder.textViewReservationDateTime = (TextView) convertView.findViewById(R.id.row_order_text_view_reservation_date);
            viewHolder.llParent = (LinearLayout) convertView.findViewById(R.id.row_order_ll1);
            viewHolder.ll2 = (LinearLayout) convertView.findViewById(R.id.row_order_ll2);
            viewHolder.imageViewAccept = (ImageView) convertView.findViewById(R.id.row_order_accept);
            viewHolder.imageViewReject = (ImageView) convertView.findViewById(R.id.row_order_reject);
            viewHolder.textViewCancel = (TextView) convertView.findViewById(R.id.row_order_text_view_cancel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final OrderModel restaurantModel = restaurantAdapterModel.get(position);
        viewHolder.textViewRestaurantName.setText("Restaurant Name : " + restaurantModel.getRestaurants_name());
        viewHolder.textViewUserName.setText("Name : " + restaurantModel.getUserName());
        viewHolder.textViewDateTime.setText("Order Date : " + restaurantModel.getOrder_date_time());
        viewHolder.textViewReservationDateTime.setText("Reservation Time : " + restaurantModel.getReservationDateTime());
        final int approvalCode = restaurantModel.getApproval_cde();
        final int orderId = restaurantModel.getOrder_id();
        if (userType.equalsIgnoreCase(Constants.USER_TYPE_USER)) {

            viewHolder.textViewRestaurantName.setVisibility(View.VISIBLE);
            viewHolder.ll2.setVisibility(View.GONE);
            viewHolder.textViewUserName.setVisibility(View.GONE);
            if (approvalCode == 2) {
                viewHolder.textViewCancel.setVisibility(View.GONE);
                viewHolder.llParent.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
            } else if (approvalCode == 3) {
                viewHolder.textViewCancel.setVisibility(View.GONE);
                viewHolder.llParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            } else if (approvalCode == 4) {
                viewHolder.textViewCancel.setVisibility(View.GONE);
                viewHolder.llParent.setBackgroundColor(ContextCompat.getColor(context, R.color.fluorogreen));
            } else {
                viewHolder.textViewCancel.setVisibility(View.VISIBLE);
                viewHolder.llParent.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
            viewHolder.imageViewAccept.setOnClickListener(null);
            viewHolder.imageViewReject.setOnClickListener(null);
            viewHolder.textViewCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (orderFragment != null) {
                        orderFragment.orderApprove(position, 4, orderId);
                    }
                }
            });

        } else {
            viewHolder.textViewCancel.setVisibility(View.GONE);
            viewHolder.textViewRestaurantName.setVisibility(View.GONE);
            viewHolder.textViewUserName.setVisibility(View.VISIBLE);
            viewHolder.llParent.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            if (approvalCode == 2) {
                viewHolder.ll2.setVisibility(View.GONE);
                viewHolder.llParent.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
            } else if (approvalCode == 3) {
                viewHolder.ll2.setVisibility(View.GONE);
                viewHolder.llParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            } else if (approvalCode == 4) {
                viewHolder.ll2.setVisibility(View.GONE);
                viewHolder.llParent.setBackgroundColor(ContextCompat.getColor(context, R.color.fluorogreen));
            } else {
                viewHolder.ll2.setVisibility(View.VISIBLE);
                viewHolder.llParent.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
            viewHolder.textViewCancel.setOnClickListener(null);
            viewHolder.imageViewAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderFragment != null) {
                        orderFragment.orderApprove(position, 2, orderId);
                    }
                }
            });

            viewHolder.imageViewReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderFragment != null) {
                        orderFragment.orderApprove(position, 3, orderId);
                    }
                }
            });


        }


        return convertView;
    }

    private class ViewHolder {
        private TextView textViewRestaurantName;
        private TextView textViewUserName;
        private TextView textViewDateTime;
        private TextView textViewReservationDateTime;
        private LinearLayout llParent;
        private LinearLayout ll2;
        private ImageView imageViewAccept;
        private ImageView imageViewReject;
        private TextView textViewCancel;

    }

    public void updateApprovalCode(final int position, final int approvalCode, final int orderId) {
        if (restaurantAdapterModel != null && !restaurantAdapterModel.isEmpty()) {
            final OrderModel orderModel = restaurantAdapterModel.get(position);
            orderModel.setApproval_cde(approvalCode);
            notifyDataSetChanged();
            if (approvalCode == 2) {
                Utils.sendSMSMessage(orderModel.getContactNumber(), "You order of " + orderModel.getRestaurants_name() + " has approved");
            } else {
                Utils.sendSMSMessage(orderModel.getContactNumber(), "You order of " + orderModel.getRestaurants_name() + " has rejected");
            }

        }
    }


}


