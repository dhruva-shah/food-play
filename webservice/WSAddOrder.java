package com.restaurantsapp.webservice;

import android.content.Context;

import com.restaurantsapp.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Asus on 07-02-2016.
 */
public class WSAddOrder {
    private boolean isError;
    private String message;
    private Context context;
    private boolean isLogout;

    public boolean isLogout() {
        return isLogout;
    }

    public WSAddOrder(final Context context) {
        this.context = context;
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }

    private RequestBody updateProfile(final String restaurants_id, final String restaurants_name, final String rm_user_id,
                                      final String no_of_persons, final String reservation_point, final String reservation_time) {
        final WSConstant wsConstant = new WSConstant();
        final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
        final String userId = preferenceUtils.getString(PreferenceUtils.USER_ID);
        final String token = preferenceUtils.getString(PreferenceUtils.TOKEN);
        final String userName = preferenceUtils.getString(PreferenceUtils.USER_NAME);
        final String userNumber = preferenceUtils.getString(PreferenceUtils.USER_CONTACT_NUMBER);

        final FormBody.Builder builder = new FormBody.Builder();
        builder.add(wsConstant.PARAMS_USER_ID, userId);
        builder.add(wsConstant.PARAMS_TOKEN, token);
        builder.add(wsConstant.PARAMS_RESTAURANT_ID, restaurants_id);
        builder.add(wsConstant.PARAMS_RESTAURANT_NAME, restaurants_name);
        builder.add(wsConstant.PARAMS_RM_USER_ID, rm_user_id);
        builder.add(wsConstant.PARAMS_NO_OF_PERSONS, no_of_persons);
        builder.add(wsConstant.PARAMS_RESERVATION_POINT, reservation_point);
        builder.add(wsConstant.PARAMS_RESERVATION_TIME, reservation_time);
        builder.add(wsConstant.PARAMS_USER_NAME, userName);
        builder.add(wsConstant.PARAMS_CONTACT_NUMBER, userNumber);
        final RequestBody formBody = builder.build();
        return formBody;
    }


    public void executeService(final String restaurants_id, final String restaurants_name, final String rm_user_id,
                               final String no_of_persons, final String reservation_point, final String reservation_time) {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_ORDER_RESTAURANTS;
        final String response = wsUtil.wsPost(url, updateProfile(restaurants_id, restaurants_name, rm_user_id, no_of_persons, reservation_point, reservation_time));
        parseResponse(response);
    }

    private void parseResponse(final String response) {
        try {
            final JSONObject jsonObject = new JSONObject(response);
            final WSConstant wsConstant = new WSConstant();
            isError = jsonObject.getBoolean(wsConstant.PARAMS_ERROR);
            message = jsonObject.getString(wsConstant.PARAMS_ERROR_MSG);
            if (isError) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
