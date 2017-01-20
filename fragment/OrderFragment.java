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
import com.restaurantsapp.adapter.OrderListAdapter;
import com.restaurantsapp.model.OrderModel;
import com.restaurantsapp.model.RestaurantModel;
import com.restaurantsapp.utils.Utils;
import com.restaurantsapp.webservice.WSApproveOrder;
import com.restaurantsapp.webservice.WSGetOrder;

import java.util.ArrayList;

/**
 * Created by Admin on 26-03-2016.
 */
public class OrderFragment extends MainFragment {

    private ListView restListListview;
    private OrderListAdapter orderListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurats, null);
    }

    @Override
    public void initView(View view) {
        initActionBar();
        restListListview = (ListView) view.findViewById(R.id.fragment_restaurats_listview);
        if (Utils.isOnline(getActivity())) {
            final AsyncOrderList asyncOrderList = new AsyncOrderList();
            asyncOrderList.execute();
        } else {
            Utils.displayNoInternetDialog(getActivity());
        }

        restListListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final OrderModel orderModel = (OrderModel) adapterView.getItemAtPosition(i);
                if (orderModel != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putParcelable("orderModel", orderModel);
                    final ViewOrderFragment viewOrderFragment = new ViewOrderFragment();
                    viewOrderFragment.setArguments(bundle);
                    ((MainActivity) getActivity()).addFragment(viewOrderFragment, OrderFragment.this);
                }
            }
        });


    }

    @Override
    public void initActionBar() {
        ((MainActivity) getActivity()).setTopBar(getString(R.string.booking), true);
    }


    private class AsyncOrderList extends AsyncTask<Void, Void, ArrayList<OrderModel>> {
        private ProgressDialog progressDialog;
        private WSGetOrder wsGetOrder;
        private Boolean isError;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utils.showProgressDialog(getActivity(), getString(R.string.loading), false);
        }

        @Override
        protected ArrayList<OrderModel> doInBackground(Void... params) {
            wsGetOrder = new WSGetOrder(getActivity());
            return wsGetOrder.executeService();

        }

        @Override
        protected void onPostExecute(ArrayList<OrderModel> login) {
            super.onPostExecute(login);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (wsGetOrder != null) {
                isError = wsGetOrder.isError();
                if (isError) {
                    orderListAdapter = new OrderListAdapter(OrderFragment.this, login);
                    restListListview.setAdapter(orderListAdapter);
                } else if (wsGetOrder.isLogout()) {
                    Utils.displayLogoutAlertDialog(getActivity(), wsGetOrder.getMessage());
                } else {
                    Utils.displayAlertDialog(getActivity(), "", wsGetOrder.getMessage());
                }
            }
        }
    }


    public void orderApprove(final int position, final int approvalCode, final int orderId) {
        if (Utils.isOnline(getActivity())) {
            final AsyncApproveOrder asyncApproveOrder = new AsyncApproveOrder();
            asyncApproveOrder.execute(position, approvalCode, orderId);
        } else {
            Utils.displayNoInternetDialog(getActivity());
        }
    }

    private class AsyncApproveOrder extends AsyncTask<Integer, Void, Void> {
        private ProgressDialog progressDialog;
        private WSApproveOrder wsGetOrder;
        private Boolean isError;
        private int position;
        private int approvalCode;
        private int orderId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utils.showProgressDialog(getActivity(), getString(R.string.loading), false);
        }

        @Override
        protected Void doInBackground(Integer... params) {
            wsGetOrder = new WSApproveOrder(getActivity());
            position = params[0];
            approvalCode = params[1];
            orderId = params[2];
            wsGetOrder.executeService("" + params[1], "" + params[2]);
            return null;
        }

        @Override
        protected void onPostExecute(Void login) {
            super.onPostExecute(login);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (wsGetOrder != null) {
                isError = wsGetOrder.isError();
                if (isError) {
                    if (orderListAdapter != null) {
                        orderListAdapter.updateApprovalCode(position, approvalCode, orderId);
                    }


                    Utils.displayAlertDialog(getActivity(), "", wsGetOrder.getMessage());
                } else if (wsGetOrder.isLogout()) {
                    Utils.displayLogoutAlertDialog(getActivity(), wsGetOrder.getMessage());
                } else {
                    Utils.displayAlertDialog(getActivity(), "", wsGetOrder.getMessage());
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
