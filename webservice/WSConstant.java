package com.restaurantsapp.webservice;

import okhttp3.MediaType;

/**
 * Created by indianic on 04/02/16.
 */
public class WSConstant {

    public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    public final static int CONNECTION_TIMEOUT = 1000 * 30;
    public static final String BASE_URL = " http://parking.comuv.com/Parking/";
    public static final String BASE_URL_BANNER = "http://parking.comuv.com/upload/restaurantsbanner/";
    public static final String BASE_URL_MENU = "http://parking.comuv.com/upload/restaurantsmenu/";
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static final String METHOD_LOGIN = "login.php";
    public static final String METHOD_REGISTER = "register.php";
    public static final String METHOD_APPROVE_BOOKING = "approveorder.php";
    public static final String METHOD_GET_RESTAURANTS = "getrestaurants.php";
    public static final String METHOD_REGISTER_RESTAURANT = "registerrestaurants.php";
    public static final String METHOD_ADD_RESTAURANT = "registerrestaurants.php";
    public static final String METHOD_UPDATE_PROFILE = "updateprofile.php";
    public static final String METHOD_UPDATE_RESTAURANT = "updaterestaurants.php";
    public static final String METHOD_DELETE_RESTAURANT = "deleterestaurants.php";
    public static final String METHOD_LOGOUT = "logout.php";
    public static final String METHOD_GET_ORDER = "getorder.php";
    public static final String METHOD_GAME = "getgamequestion.php";
    public static final String METHOD_ORDER_RESTAURANTS = "orderrestaurants.php";


    public final String PARAMS_EMAIL = "email";
    public final String PARAMS_ERROR = "error";
    public final String PARAMS_IS_LOGOUT = "is_logout";
    public final String PARAMS_ERROR_MSG = "error_msg";
    public final String PARAMS_DATA = "data";
    public final String PARAMS_PASSWORD = "password";
    public final String PARAMS_USER_ID = "user_id";
    public final String PARAMS_USER_NAME = "user_name";
    public final String PARAMS_USER_PROFILE = "user_profile";
    public final String PARAMS_USER_CONTACT = "user_contact";
    public final String PARAMS_USER_TYPE = "user_type";
    public final String PARAMS_TOKEN = "token";
    public final String PARAMS_ORDER_ID = "order_id";
    public final String PARAMS_APPROVAL_CODE = "approval_code";
    public final String PARAMS_RESTAURANT_ID = "restaurants_id";
    public final String PARAMS_RESTAURANT_NAME = "restaurants_name";
    public final String PARAMS_RESTAURANT_BANNER1 = "restaurants_banner_1";
    public final String PARAMS_RESTAURANT_BANNER2 = "restaurants_banner_2";
    public final String PARAMS_RESTAURANT_BANNER3 = "restaurants_banner_3";
    public final String PARAMS_RESTAURANT_BANNER4 = "restaurants_banner_4";
    public final String PARAMS_RESTAURANT_BANNER5 = "restaurants_banner_5";

    public final String PARAMS_RESTAURANT_MENU1 = "restaurants_menu_images_1";
    public final String PARAMS_RESTAURANT_MENU2 = "restaurants_menu_images_2";
    public final String PARAMS_RESTAURANT_MENU3 = "restaurants_menu_images_3";
    public final String PARAMS_RESTAURANT_MENU4 = "restaurants_menu_images_4";
    public final String PARAMS_RESTAURANT_MENU5 = "restaurants_menu_images_5";

    public final String PARAMS_RM_USER_ID = "rm_user_id";
    public final String PARAMS_ORDER_DATE_TIME = "order_date_time";
    public final String PARAMS_NO_OF_PERSONS = "no_of_persons";
    public final String PARAMS_RESERVATION_POINT = "reservation_point";
    public final String PARAMS_RESERVATION_TIME = "reservation_time";

    public final String PARAMS_CONTACT_NUMBER = "contact_number";


    public final String PARAMS_GAME_QUES_ID = "game_ques_id";
    public final String PARAMS_GAME_QUES = "game_ques";
    public final String PARAMS_GAME_OPTN_A = "game_option_a";
    public final String PARAMS_GAME_OPTN_B = "game_option_b";
    public final String PARAMS_GAME_OPTN_C = "game_option_c";
    public final String PARAMS_GAME_OPTN_D = "game_option_d";
    public final String PARAMS_GAME_ANS = "game_ans";
    public final String PARAMS_GAME_PTS = "game_point";


    public final String PARAMS_RESTAURANTS_BANNER_ = "restaurants_banner_";
    public final String PARAMS_RESTAURANTS_MENU_IMAGES_ = "restaurants_menu_images_";


}
