package com.restaurantsapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restaurantsapp.R;
import com.restaurantsapp.activity.MainActivity;
import com.restaurantsapp.model.OrderModel;
import com.restaurantsapp.utils.Constants;

/**
 * Created by Admin on 30-03-2016.
 */
public class ViewOrderFragment extends MainFragment {

    private OrderModel orderModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderModel = getArguments().getParcelable("orderModel");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_order, null);
    }

    @Override
    public void initView(View view) {
        final TextView textViewName = (TextView) view.findViewById(R.id.fragment_view_ordre_res_name);
        final TextView textViewTime = (TextView) view.findViewById(R.id.fragment_view_ordre_res_time);
        final TextView textViewNoOfPerson = (TextView) view.findViewById(R.id.fragment_view_ordre_no_of_person);
        final TextView textViewStatus = (TextView) view.findViewById(R.id.fragment_view_ordre_status);


        if (orderModel != null) {
            textViewName.setText("Restaurant Name : " + orderModel.getRestaurants_name());
            textViewTime.setText("Reservation Time : " + orderModel.getReservationDateTime());
            textViewNoOfPerson.setText("Number of Person : " + orderModel.getNoOfPersons());
            final String approvalCode = "" + orderModel.getApproval_cde();

            if (approvalCode.equalsIgnoreCase("1")) {
                textViewStatus.setText("Status : Pending");
            } else if (approvalCode.equalsIgnoreCase("2")) {
                textViewStatus.setText("Status : Accepted");
            } else if (approvalCode.equalsIgnoreCase("3")) {
                textViewStatus.setText("Status : Rejected");
            }

        }

    }

    @Override
    public void initActionBar() {
        ((MainActivity) getActivity()).setTopBar(getString(R.string.view_order), false);
    }
}
