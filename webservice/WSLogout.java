package com.restaurantsapp.webservice;

import android.content.Context;

import com.restaurantsapp.utils.PreferenceUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Asus on 07-02-2016.
 */
public class WSLogout {
    private boolean isError;
    private String message;
    private Context context;
    private boolean isLogout;

    public boolean isLogout() {
        return isLogout;
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }

    public WSLogout(final Context context) {
        this.context = context;
    }

    private RequestBody generateLogout() {
        final WSConstant wsConstant = new WSConstant();
        final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
        final String userId = preferenceUtils.getString(PreferenceUtils.USER_ID);
        final String token = preferenceUtils.getString(PreferenceUtils.TOKEN);
        final RequestBody formBody = new FormBody.Builder()
                .add(wsConstant.PARAMS_TOKEN, token)
                .add(wsConstant.PARAMS_USER_ID, userId)
                .build();
        return formBody;
    }


    public void executeService() {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_LOGOUT;
        final String response = wsUtil.wsPost(url, generateLogout());
        parseResponse(response);
    }

    private void parseResponse(final String response) {
        try {
            final JSONObject jsonObject = new JSONObject(response);
            final WSConstant wsConstant = new WSConstant();
            isError = jsonObject.optBoolean(wsConstant.PARAMS_ERROR);
            message = jsonObject.optString(wsConstant.PARAMS_ERROR_MSG);
            isLogout = jsonObject.optBoolean(wsConstant.PARAMS_IS_LOGOUT);
            if (isError) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
