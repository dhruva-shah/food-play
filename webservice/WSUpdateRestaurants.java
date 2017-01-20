package com.restaurantsapp.webservice;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Asus on 07-02-2016.
 */
public class WSUpdateRestaurants {
    private boolean isError;
    private String message;

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }
    //MENUPIC
    //how to use bitmap

    private RequestBody updateRestaurant(final String token, final String restaurant_id,final String restaurant_name,final String restb1,final String restb2,final String restb3,final String restb4,final String restb5,final String restm1,final String restm2,final String restm3,final String restm4,final String restm5) {
        final WSConstant wsConstant = new WSConstant();
        final RequestBody formBody = new FormBody.Builder()
                .add(wsConstant.PARAMS_TOKEN, token)
                .add(wsConstant.PARAMS_RESTAURANT_ID, restaurant_id)
                .add(wsConstant.PARAMS_RESTAURANT_NAME, restaurant_name)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1,restb1)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1,restb2)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1,restb3)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1,restb4)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1,restb5)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1,restm1)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1,restm2)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1,restm3)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1,restm4)
                .add(wsConstant.PARAMS_RESTAURANT_BANNER1,restm5)
                .build();
        return formBody;
    }


    public void executeService(final String token, final String restaurant_id,final String restaurant_name,final String restb1,final String restb2,final String restb3,final String restb4,final String restb5,final String restm1,final String restm2,final String restm3,final String restm4,final String restm5) {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_UPDATE_RESTAURANT;
        final String response = wsUtil.wsPost(url, updateRestaurant(token, restaurant_id, restaurant_name,restb1,restb2,restb3,restb4,restb5,restm1,restm2,restm3,restm4,restm5));
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
