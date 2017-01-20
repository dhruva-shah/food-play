package com.restaurantsapp.webservice;

import android.content.Context;

import com.restaurantsapp.model.RestaurantModel;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by indianic on 11/03/16.
 */
public class WSDeleteRestaurant {
    private Context context;
    private boolean isError;
    private String message;


    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }

    public WSDeleteRestaurant(final Context context) {
        this.context = context;
    }


    private RequestBody deleteRestaurant(final String token, final String userid, final String restaurantid) {
        final WSConstant wsConstant = new WSConstant();
        final RequestBody formBody = new FormBody.Builder()
                .add(wsConstant.PARAMS_TOKEN, token)
                .add(wsConstant.PARAMS_USER_ID, userid)
                .add(wsConstant.PARAMS_RESTAURANT_ID, restaurantid)
                .build();
        return formBody;
    }


    public void executeService(final String token, final String userid, final String restaurantid) {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_DELETE_RESTAURANT;
        final String response = wsUtil.wsPost(url, deleteRestaurant(token, userid, restaurantid));
        parseResponse(response);
    }

    private void parseResponse(final String response) {

        final ArrayList<RestaurantModel> rest_list = new ArrayList<RestaurantModel>();
        try {
            final JSONObject jsonObject = new JSONObject(response);
            final WSConstant wsConstant = new WSConstant();
            isError = jsonObject.optBoolean(wsConstant.PARAMS_ERROR);
            message = jsonObject.optString(wsConstant.PARAMS_ERROR_MSG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
