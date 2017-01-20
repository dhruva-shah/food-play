package com.restaurantsapp.webservice;

import android.content.Context;
import android.text.TextUtils;

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
public class WSGetRestaurants {


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

    public WSGetRestaurants(final Context context) {
        this.context = context;
    }


    private RequestBody getRestaurants() {
        final WSConstant wsConstant = new WSConstant();
        final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
        final String userId = preferenceUtils.getString(PreferenceUtils.USER_ID);
        final String userType = preferenceUtils.getString(PreferenceUtils.USER_TYPE);
        final String token = preferenceUtils.getString(PreferenceUtils.TOKEN);

        final RequestBody formBody = new FormBody.Builder()
                .add(wsConstant.PARAMS_TOKEN, token)
                .add(wsConstant.PARAMS_USER_TYPE, userType)
                .add(wsConstant.PARAMS_USER_ID, userId)
                .build();
        return formBody;
    }


    public ArrayList<RestaurantModel> executeService() {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_GET_RESTAURANTS;
        final String response = wsUtil.wsPost(url, getRestaurants());
        return parseResponse(response);
    }

    private ArrayList<RestaurantModel> parseResponse(final String response) {

        final ArrayList<RestaurantModel> rest_list = new ArrayList<RestaurantModel>();
        try {
            final JSONObject jsonObject = new JSONObject(response);
            final WSConstant wsConstant = new WSConstant();
            isError = jsonObject.optBoolean(wsConstant.PARAMS_ERROR);
            isLogout = jsonObject.optBoolean(wsConstant.PARAMS_IS_LOGOUT);
            message = jsonObject.optString(wsConstant.PARAMS_ERROR_MSG);
            RestaurantModel restaurantAdapterModel;


            if (isError) {
                final JSONArray jsonArray = jsonObject.optJSONArray(wsConstant.PARAMS_DATA);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonObjectData = jsonArray.optJSONObject(i);
                    final int restaurant_id = jsonObjectData.optInt(wsConstant.PARAMS_RESTAURANT_ID);
                    final String restaurant_name = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_NAME);
                    final String rmUserId = jsonObjectData.optString(wsConstant.PARAMS_USER_ID);

                    final String rest_banner1 = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_BANNER1);
                    final String rest_banner2 = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_BANNER2);
                    final String rest_banner3 = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_BANNER3);
                    final String rest_banner4 = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_BANNER4);
                    final String rest_banner5 = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_BANNER5);
                    final String contactNumber = jsonObjectData.optString(wsConstant.PARAMS_CONTACT_NUMBER);

                    final ArrayList<String> restimgurl = new ArrayList<>();
                    if (!TextUtils.isEmpty(rest_banner1)) {
                        restimgurl.add(wsConstant.BASE_URL_BANNER + rest_banner1);
                    }
                    if (!TextUtils.isEmpty(rest_banner2)) {
                        restimgurl.add(wsConstant.BASE_URL_BANNER + rest_banner2);
                    }
                    if (!TextUtils.isEmpty(rest_banner3)) {
                        restimgurl.add(wsConstant.BASE_URL_BANNER + rest_banner3);
                    }
                    if (!TextUtils.isEmpty(rest_banner4)) {
                        restimgurl.add(wsConstant.BASE_URL_BANNER + rest_banner4);
                    }
                    if (!TextUtils.isEmpty(rest_banner5)) {
                        restimgurl.add(wsConstant.BASE_URL_BANNER + rest_banner5);
                    }

                    final ArrayList<String> restmenuurl = new ArrayList<>();
                    final String rest_menu1 = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_MENU1);
                    final String rest_menu2 = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_MENU2);
                    final String rest_menu3 = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_MENU3);
                    final String rest_menu4 = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_MENU4);
                    final String rest_menu5 = jsonObjectData.optString(wsConstant.PARAMS_RESTAURANT_MENU5);
                    if (!TextUtils.isEmpty(rest_menu1)) {
                        restmenuurl.add(wsConstant.BASE_URL_MENU + rest_menu1);
                    }
                    if (!TextUtils.isEmpty(rest_menu2)) {
                        restmenuurl.add(wsConstant.BASE_URL_MENU + rest_menu2);
                    }
                    if (!TextUtils.isEmpty(rest_menu3)) {
                        restmenuurl.add(wsConstant.BASE_URL_MENU + rest_menu3);
                    }
                    if (!TextUtils.isEmpty(rest_menu4)) {
                        restmenuurl.add(wsConstant.BASE_URL_MENU + rest_menu4);
                    }
                    if (!TextUtils.isEmpty(rest_menu5)) {
                        restmenuurl.add(wsConstant.BASE_URL_MENU + rest_menu5);
                    }
                    restaurantAdapterModel = new RestaurantModel(restaurant_id, restaurant_name, rest_banner1, restimgurl, restmenuurl, rmUserId, contactNumber);
                    rest_list.add(restaurantAdapterModel);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest_list;
    }
}
