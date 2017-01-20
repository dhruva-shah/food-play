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
 * Created by Asus on 07-02-2016.
 */
public class WSUpdateProfile {
    private boolean isError;
    private String message;
    private Context context;
    private boolean isLogout;

    public boolean isLogout() {
        return isLogout;
    }

    public WSUpdateProfile(final Context context) {
        this.context = context;
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }

    private RequestBody updateProfile(final String user_name, final String user_contact, final String user_profile) {
        final WSConstant wsConstant = new WSConstant();
//        final RequestBody formBody = new FormBody.Builder()
//                .add(wsConstant.PARAMS_USER_ID, user_id)
//                .add(wsConstant.PARAMS_TOKEN, token)
//                .add(wsConstant.PARAMS_USER_NAME, user_name)
//                .add(wsConstant.PARAMS_USER_CONTACT, user_contact)
//                .add(wsConstant.PARAMS_USER_PROFILE, user_profile)
//                .build();

        final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
        final String userId = preferenceUtils.getString(PreferenceUtils.USER_ID);
        final String token = preferenceUtils.getString(PreferenceUtils.TOKEN);

        final MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart(wsConstant.PARAMS_USER_ID, userId);
        builder.addFormDataPart(wsConstant.PARAMS_TOKEN, token);
        builder.addFormDataPart(wsConstant.PARAMS_USER_NAME, user_name);
        builder.addFormDataPart(wsConstant.PARAMS_USER_CONTACT, user_contact);

        if (!TextUtils.isEmpty(user_profile)) {
            final File file = new File(user_profile);
            if (file != null && file.exists()) {
                builder.addFormDataPart(wsConstant.PARAMS_USER_PROFILE, user_profile,
                        RequestBody.create(WSConstant.MEDIA_TYPE_JPG, new File(user_profile)));
            }
        }
//        else {
//            builder.addFormDataPart(wsConstant.PARAMS_USER_PROFILE, user_profile);
//        }
        final RequestBody formBody = builder.build();
        return formBody;
    }


    public void executeService(final String user_name, final String user_contact, final String user_profile) {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_UPDATE_PROFILE;
        final String response = wsUtil.wsPost(url, updateProfile(user_name, user_contact, user_profile));
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
                final String userName = jsonObjectData.optString(wsConstant.PARAMS_USER_NAME);
                final String userProfile = jsonObjectData.optString(wsConstant.PARAMS_USER_PROFILE);
                final String userContact = jsonObjectData.optString(wsConstant.PARAMS_USER_CONTACT);

                final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
                preferenceUtils.setString(PreferenceUtils.USER_NAME, userName);
                preferenceUtils.setString(PreferenceUtils.PROFILE_URL, userProfile);
                preferenceUtils.setString(PreferenceUtils.USER_CONTACT_NUMBER, userContact);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
