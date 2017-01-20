package com.restaurantsapp.webservice;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Asus on 07-02-2016.
 */
public class WSRegisterRestaurant {
    private boolean isError;
    private String message;

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }
//MENUPIC
    private RequestBody registerRestaurant(final String restaurant_name, final String user_id,final String restaurants_banner_1,final String restaurants_menu_images_1,final String token) {
        final WSConstant wsConstant = new WSConstant();
        final RequestBody formBody = new FormBody.Builder()
                .add(wsConstant.PARAMS_RESTAURANT_NAME, restaurant_name)
                .add(wsConstant.PARAMS_USER_ID, user_id)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1, restaurants_banner_1)
                .add(wsConstant.PARAMS_RESTAURANT_MENU1, restaurants_menu_images_1)
                .add(wsConstant.PARAMS_TOKEN, token)
                .build();
        return formBody;
    }


    public void executeService(final String restaurant_name, final String user_id,final String restaurants_banner_1,final String restaurants_menu_images_1,final String token) {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_REGISTER_RESTAURANT;
        final String response = wsUtil.wsPost(url, registerRestaurant(restaurant_name,user_id,restaurants_banner_1,restaurants_menu_images_1,token));
        parseResponse(response);
    }

    private void parseResponse(final String response) {
        try {
            final JSONObject jsonObject = new JSONObject(response);
            final WSConstant wsConstant = new WSConstant();
            isError = jsonObject.getBoolean(wsConstant.PARAMS_ERROR);
            message = jsonObject.getString(wsConstant.PARAMS_ERROR_MSG);

            if (isError) {
                final JSONArray jsonArray = jsonObject.getJSONArray(wsConstant.PARAMS_DATA);
                final JSONObject jsonObjectData = jsonArray.getJSONObject(0);
                final int userId = jsonObjectData.getInt(wsConstant.PARAMS_USER_ID);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
