//package com.restaurantsapp.webservice;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import okhttp3.FormBody;
//import okhttp3.RequestBody;
//
///**
// * Created by Asus on 07-02-2016.
// */
//public class WSApproveBooking {
//    private boolean isError;
//    private String message;
//
//    public boolean isError() {
//        return isError;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    private RequestBody approveOrder(final String order_id,final String approval_code,final String rm_user_id,final String token) {
//        final WSConstant wsConstant = new WSConstant();
//        final RequestBody formBody = new FormBody.Builder()
//                .add(wsConstant.PARAMS_ORDER_ID, order_id)
//                .add(wsConstant.PARAMS_APPROVAL_CODE, approval_code)
//                .add(wsConstant.PARAMS_RM_USER_ID, rm_user_id)
//                .add(wsConstant.PARAMS_TOKEN, token)
//                .build();
//        return formBody;
//    }
//
//
//    public void executeService(final String order_id,final String approval_code,final String rm_user_id,final String token) {
//        final WSUtil wsUtil = new WSUtil();
//        final String url = WSConstant.BASE_URL + WSConstant.METHOD_APPROVE_BOOKING;
//        final String response = wsUtil.wsPost(url, approveOrder(order_id,approval_code,rm_user_id,token));
//        parseResponse(response);
//    }
//
//    private void parseResponse(final String response) {
//        try {
//            final JSONObject jsonObject = new JSONObject(response);
//            final WSConstant wsConstant = new WSConstant();
//            isError = jsonObject.getBoolean(wsConstant.PARAMS_ERROR);
//            message = jsonObject.getString(wsConstant.PARAMS_ERROR_MSG);
//
//            if (isError) {
//                //wwwhhhaaattttttttttttttttttttt
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
