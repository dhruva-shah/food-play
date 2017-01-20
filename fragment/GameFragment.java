package com.restaurantsapp.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.restaurantsapp.R;
import com.restaurantsapp.activity.LoginActivity;
import com.restaurantsapp.activity.MainActivity;
import com.restaurantsapp.model.GameModel;
import com.restaurantsapp.model.RestaurantModel;
import com.restaurantsapp.utils.PreferenceUtils;
import com.restaurantsapp.utils.Utils;
import com.restaurantsapp.webservice.WSGame;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by indianic on 25/03/16.
 */
public class GameFragment extends MainFragment {
    private ArrayList<GameModel> quesList;
    private int score = 0;
    private int qid = 0;
    private GameModel currentQ;
    private TextView txtQuestion, times, scored;
    private Button button1, button2, button3, button4;
    private CounterClass timer;
    private RestaurantModel restaurantModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurantModel = getArguments().getParcelable("restaurantSelected");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, null);
    }

    public void initView(final View view) {
        initActionBar();
        txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);
        button3 = (Button) view.findViewById(R.id.button3);
        button4 = (Button) view.findViewById(R.id.button4);
        final Button buttonSkip = (Button) view.findViewById(R.id.buttonSkip);
        scored = (TextView) view.findViewById(R.id.score);
        times = (TextView) view.findViewById(R.id.timers);
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameResultFragment();
            }
        });
//        setQuestionView();
        times.setText("00:01:00");
        timer = new CounterClass(30000, 1000);
        timer.start();

        scored.setText("Score : " + score);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer("1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer("2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer("3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer("4");
            }
        });


        if (Utils.isOnline(getActivity())) {
            final AsyncGameQues asyncGameQues = new AsyncGameQues();
            asyncGameQues.execute();
        } else {
            Utils.displayNoInternetDialog(getActivity());
        }

    }

    @Override
    public void initActionBar() {
        ((MainActivity) getActivity()).setTopBar(getString(R.string.questions), false);
    }

    public void getAnswer(String AnswerString) {
        if (currentQ.getANSWER().equals(AnswerString)) {
            score = score + 10;
            scored.setText("Score : " + score);
        }
        if (qid < 5) {
            currentQ = quesList.get(qid);
            setQuestionView();
            qid++;
        } else {
            goToGameResultFragment();
        }
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish() {
            times.setText("Time is up");
            if (qid < 5) {
                currentQ = quesList.get(qid);
                setQuestionView();
                qid++;
            } else {
                goToGameResultFragment();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            long millis = millisUntilFinished;
            String hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));
            System.out.println(hms);
            times.setText(hms);
        }
    }

    private void setQuestionView() {
        // the method which will put all things together
        txtQuestion.setText(currentQ.getQUESTION());
        button1.setText(currentQ.getOPTA());
        button2.setText(currentQ.getOPTB());
        button3.setText(currentQ.getOPTC());
        button4.setText(currentQ.getOPTD());
        qid++;
    }


    private class AsyncGameQues extends AsyncTask<Void, Void, ArrayList<GameModel>> {
        private ProgressDialog progressDialog;
        private WSGame wsGame;
        private Boolean isError;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utils.showProgressDialog(getActivity(), getString(R.string.loading), false);

        }

        @Override
        protected ArrayList<GameModel> doInBackground(Void... params) {
            wsGame = new WSGame(getActivity());
            return wsGame.executeService();

        }

        @Override
        protected void onPostExecute(ArrayList<GameModel> game) {
            super.onPostExecute(game);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (wsGame != null) {
                isError = wsGame.isError();
                if (isError) {
                    quesList = game;
                    currentQ = quesList.get(0);
                    setQuestionView();
                }


            }
        }

    }


    private void goToGameResultFragment() {
        if (timer != null) {
            timer.cancel();
        }
        final GameResultFragment gameResultFragment = new GameResultFragment();
        Bundle b = new Bundle();
        b.putInt("score", score);
        b.putParcelable("restaurantSelected", restaurantModel);
        gameResultFragment.setArguments(b);
        gameResultFragment.setCancelable(false);
        gameResultFragment.show(getFragmentManager(), gameResultFragment.getClass().getSimpleName());
//        ((MainActivity) getActivity()).addFragment(gameResultFragment, getFragmentManager().findFragmentById(R.id.container));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
    }


}



