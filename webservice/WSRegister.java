package com.restaurantsapp.webservice;

import android.content.Context;
import android.text.TextUtils;

import com.restaurantsapp.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by indianic on 04/02/16.
 */
public class WSRegister {
    private boolean isError;
    private String message;
    private Context context;

    public WSRegister(final Context context) {
        this.context = context;
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }

    private RequestBody generateRegister(final String email, final String password, final String user_name, final String user_contact, final String user_type, final String user_profile) {
        final WSConstant wsConstant = new WSConstant();
//        final RequestBody formBody = new FormBody.Builder()
//                .add(wsConstant.PARAMS_EMAIL, email)
//                .add(wsConstant.PARAMS_PASSWORD, password)
//                .add(wsConstant.PARAMS_USER_NAME, user_name)
//                .add(wsConstant.PARAMS_USER_CONTACT, user_contact)
//                .add(wsConstant.PARAMS_USER_TYPE, user_type)
//                .add(wsConstant.PARAMS_USER_PROFILE, user_profile)
//                .build();

        final MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(wsConstant.PARAMS_EMAIL, email)
                .addFormDataPart(wsConstant.PARAMS_PASSWORD, password)
                .addFormDataPart(wsConstant.PARAMS_USER_NAME, user_name)
                .addFormDataPart(wsConstant.PARAMS_USER_CONTACT, user_contact)
                .addFormDataPart(wsConstant.PARAMS_USER_TYPE, user_type);
        if (!TextUtils.isEmpty(user_profile)) {
            builder.addFormDataPart(wsConstant.PARAMS_USER_PROFILE, user_profile,
                    RequestBody.create(WSConstant.MEDIA_TYPE_JPG, new File(user_profile)));
        } else {
            builder.addFormDataPart(wsConstant.PARAMS_USER_PROFILE, user_profile);
        }
        final RequestBody formBody = builder.build();

        return formBody;
    }


    public void executeService(final String email, final String password, final String user_name, final String user_contact, final String user_type, final String user_profile) {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_REGISTER;
        final String response = wsUtil.wsPost(url, generateRegister(email, password, user_name, user_contact, user_type, user_profile));
        parseResponse(response);
    }

    private void parseResponse(final String response) {
        try {
            final JSONObject jsonObject = new JSONObject(response);
            final WSConstant wsConstant = new WSConstant();
            isError = jsonObject.optBoolean(wsConstant.PARAMS_ERROR);
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
