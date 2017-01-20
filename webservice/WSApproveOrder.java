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
public class WSApproveOrder {
    private boolean isError;
    private String message;
    private Context context;
    private boolean isLogout;

    public boolean isLogout() {
        return isLogout;
    }

    public WSApproveOrder(final Context context) {
        this.context = context;
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }

    private RequestBody updateProfile(final String approvalCode, final String orderId) {
        final WSConstant wsConstant = new WSConstant();
        final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
        final String userId = preferenceUtils.getString(PreferenceUtils.USER_ID);
        final String token = preferenceUtils.getString(PreferenceUtils.TOKEN);

        final FormBody.Builder builder = new FormBody.Builder();
        builder.add(wsConstant.PARAMS_RM_USER_ID, userId);
        builder.add(wsConstant.PARAMS_TOKEN, token);
        builder.add(wsConstant.PARAMS_ORDER_ID, orderId);
        builder.add(wsConstant.PARAMS_APPROVAL_CODE, approvalCode);
        final RequestBody formBody = builder.build();
        return formBody;
    }


    public void executeService(final String approvalCode, final String orderId) {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_APPROVE_BOOKING;
        final String response = wsUtil.wsPost(url, updateProfile(approvalCode, orderId));
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
