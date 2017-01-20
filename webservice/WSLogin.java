package com.restaurantsapp.webservice;

import android.content.Context;
import android.content.SharedPreferences;

import com.restaurantsapp.R;
import com.restaurantsapp.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.String;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by indianic on 04/02/16.
 */
public class WSLogin {

    private boolean isError;
    private boolean isLogout;
    private String message;
    private Context context;

    public boolean isLogout() {
        return isLogout;
    }

    public WSLogin(final Context context) {
        this.context = context;
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }

    private RequestBody generateLogin(final String email, final String password) {
        final WSConstant wsConstant = new WSConstant();
        final RequestBody formBody = new FormBody.Builder()
                .add(wsConstant.PARAMS_EMAIL, email)
                .add(wsConstant.PARAMS_PASSWORD, password)
                .build();
        return formBody;
    }


    public void executeService(final String email, final String password) {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_LOGIN;
        final String response = wsUtil.wsPost(url, generateLogin(email, password));
        parseResponse(response);
    }

    private void parseResponse(final String response) {
        try {
            final JSONObject jsonObject = new JSONObject(response);
            final WSConstant wsConstant = new WSConstant();
            isError = jsonObject.optBoolean(wsConstant.PARAMS_ERROR);
            isLogout = jsonObject.optBoolean(wsConstant.PARAMS_IS_LOGOUT);
            message = jsonObject.optString(wsConstant.PARAMS_ERROR_MSG);
            if (isError) {
                final JSONArray jsonArray = jsonObject.getJSONArray(wsConstant.PARAMS_DATA);
                final JSONObject jsonObjectData = jsonArray.getJSONObject(0);
                final String userId = jsonObjectData.optString(wsConstant.PARAMS_USER_ID);
                final String token = jsonObjectData.optString(wsConstant.PARAMS_TOKEN);
                final String userType = jsonObjectData.optString(wsConstant.PARAMS_USER_TYPE);
                final String email = jsonObjectData.optString(wsConstant.PARAMS_EMAIL);
                final String userName = jsonObjectData.optString(wsConstant.PARAMS_USER_NAME);
                final String userProfile = jsonObjectData.optString(wsConstant.PARAMS_USER_PROFILE);
                final String userContact = jsonObjectData.optString(wsConstant.PARAMS_USER_CONTACT);

                final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
                preferenceUtils.setString(PreferenceUtils.USER_ID, userId);
                preferenceUtils.setString(PreferenceUtils.TOKEN, token);
                preferenceUtils.setString(PreferenceUtils.USER_TYPE, userType);
                preferenceUtils.setString(PreferenceUtils.EMAIL, email);
                preferenceUtils.setString(PreferenceUtils.USER_NAME, userName);
                preferenceUtils.setString(PreferenceUtils.PROFILE_URL, userProfile);
                preferenceUtils.setString(PreferenceUtils.USER_CONTACT_NUMBER, userContact);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
