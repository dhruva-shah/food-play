package com.restaurantsapp.webservice;

import android.content.Context;
import android.text.TextUtils;

import com.restaurantsapp.model.OrderModel;
import com.restaurantsapp.model.RestaurantModel;
import com.restaurantsapp.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by indianic on 04/02/16.
 */
public class WSGetOrder {


    private Context context;
    private boolean isError;
    private boolean isLogout;
    private String message;


    public boolean isError() {
        return isError;
    }

    public boolean isLogout() {
        return isLogout;
    }

    public String getMessage() {
        return message;
    }

    public WSGetOrder(final Context context) {
        this.context = context;
    }


    private RequestBody getRestaurants() {
        final WSConstant wsConstant = new WSConstant();
        final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
        final String userId = preferenceUtils.getString(PreferenceUtils.USER_ID);
        final String userType = preferenceUtils.getString(PreferenceUtils.USER_TYPE);
        final String token = preferenceUtils.getString(PreferenceUtils.TOKEN);
        final String approvalCode = "1";

        final RequestBody formBody = new FormBody.Builder()
                .add(wsConstant.PARAMS_TOKEN, token)
                .add(wsConstant.PARAMS_USER_TYPE, userType)
                .add(wsConstant.PARAMS_USER_ID, userId)
                .add(wsConstant.PARAMS_APPROVAL_CODE, approvalCode)
                .build();
        return formBody;
    }


    public ArrayList<OrderModel> executeService() {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_GET_ORDER;
        final String response = wsUtil.wsPost(url, getRestaurants());
        return parseResponse(response);
    }

    private ArrayList<OrderModel> parseResponse(final String response) {

        final ArrayList<OrderModel> rest_list = new ArrayList<OrderModel>();
        try {
            final JSONObject jsonObject = new JSONObject(response);
            final WSConstant wsConstant = new WSConstant();
            isError = jsonObject.optBoolean(wsConstant.PARAMS_ERROR);
            isLogout = jsonObject.optBoolean(wsConstant.PARAMS_IS_LOGOUT);
            message = jsonObject.optString(wsConstant.PARAMS_ERROR_MSG);


            if (isError) {
                final JSONArray jsonArray = jsonObject.optJSONArray(wsConstant.PARAMS_DATA);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonObjectData = jsonArray.optJSONObject(i);
                    final OrderModel orderModel = new OrderModel();
                    final int orderId = jsonObjectData.optInt(wsConstant.PARAMS_ORDER_ID);
                    final int restaurant_id = jsonObjectData.optInt(wsConstant.PARAMS_RESTAURANT_ID);
                    final int userId = jsonObjectData.optInt(wsConstant.PARAMS_USER_ID);
                    final String userName = jsonObjectData.optString(wsConstant.PARAMS_USER_NAME);
                    final String orderDateTime = jsonObjectData.optString(wsConstant.PARAMS_ORDER_DATE_TIME);
                    final String restaurantName = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_NAME);
                    final String noOfPersons = jsonObjectData.optString(wsConstant.PARAMS_NO_OF_PERSONS);
                    final int rmUserId = jsonObjectData.optInt(wsConstant.PARAMS_RM_USER_ID);
                    final int approvalCode = jsonObjectData.optInt(wsConstant.PARAMS_APPROVAL_CODE);
                    final String reservation_time = jsonObjectData.optString(wsConstant.PARAMS_RESERVATION_TIME);
                    final String contactNumber = jsonObjectData.optString(wsConstant.PARAMS_CONTACT_NUMBER);

                    orderModel.setOrder_id(orderId);
                    orderModel.setRestaurants_id(restaurant_id);
                    orderModel.setUser_id(userId);
                    orderModel.setOrder_date_time(orderDateTime);
                    orderModel.setRestaurants_name(restaurantName);
                    orderModel.setRm_user_id(rmUserId);
                    orderModel.setApproval_cde(approvalCode);
                    orderModel.setUserName(userName);
                    orderModel.setReservationDateTime(reservation_time);
                    orderModel.setNoOfPersons(noOfPersons);
                    orderModel.setContactNumber(contactNumber);
                    rest_list.add(orderModel);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest_list;
    }
}
