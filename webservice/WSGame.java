package com.restaurantsapp.webservice;

import android.content.Context;

import com.restaurantsapp.model.GameModel;
import com.restaurantsapp.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by indianic on 25/03/16.
 */
public class WSGame {
    private boolean isError;
    private String message;
    private Context context;
    private boolean isLogout;

    public boolean isLogout() {
        return isLogout;
    }

    public WSGame(final Context context) {
        this.context = context;
    }

    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
    }

    private RequestBody generateGame() {
        final WSConstant wsConstant = new WSConstant();

        final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
        final String token = preferenceUtils.getString(PreferenceUtils.TOKEN);
        final RequestBody formBody = new FormBody.Builder()
                .add(wsConstant.PARAMS_TOKEN, token)
                .build();
        return formBody;
    }


    public ArrayList<GameModel> executeService() {
        final WSUtil wsUtil = new WSUtil();
        final String url = WSConstant.BASE_URL + WSConstant.METHOD_GAME;
        final String response = wsUtil.wsPost(url, generateGame());
        return parseResponse(response);
    }

    private ArrayList<GameModel> parseResponse(final String response) {
        final ArrayList<GameModel> gameList = new ArrayList<>();
        try {
            final JSONObject jsonObject = new JSONObject(response);
            final WSConstant wsConstant = new WSConstant();
            isError = jsonObject.optBoolean(wsConstant.PARAMS_ERROR);
            isLogout = jsonObject.optBoolean(wsConstant.PARAMS_IS_LOGOUT);
            message = jsonObject.optString(wsConstant.PARAMS_ERROR_MSG);
            GameModel gameModel;


            if (isError) {
                final JSONArray jsonArray = jsonObject.optJSONArray(wsConstant.PARAMS_DATA);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonObjectData = jsonArray.optJSONObject(i);
                    final int game_ques_id = jsonObjectData.optInt(wsConstant.PARAMS_GAME_QUES_ID);
                    final String game_ques = jsonObjectData.optString(wsConstant.PARAMS_GAME_QUES);
                    final String game_optn_a = jsonObjectData.optString(wsConstant.PARAMS_GAME_OPTN_A);
                    final String game_optn_b = jsonObjectData.optString(wsConstant.PARAMS_GAME_OPTN_B);
                    final String game_optn_c = jsonObjectData.optString(wsConstant.PARAMS_GAME_OPTN_C);
                    final String game_optn_d = jsonObjectData.optString(wsConstant.PARAMS_GAME_OPTN_D);
                    final String game_ans = jsonObjectData.optString(wsConstant.PARAMS_GAME_ANS);

                    gameModel = new GameModel(game_ques, game_optn_a, game_optn_b, game_optn_c, game_optn_d, game_ans);
                    gameList.add(gameModel);
                }

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameList;
    }
}
