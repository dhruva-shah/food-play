package com.restaurantsapp.fragment;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.restaurantsapp.R;
import com.restaurantsapp.activity.MainActivity;
import com.restaurantsapp.model.RestaurantModel;
import com.restaurantsapp.utils.PreferenceUtils;
import com.restaurantsapp.utils.RangeTimePickerDialog;
import com.restaurantsapp.utils.Utils;
import com.restaurantsapp.webservice.WSAddOrder;

import java.util.Calendar;


/**
 * Created by indianic on 25/03/16.
 */
public class GameResultFragment extends DialogFragment {
    private int score;
    private RestaurantModel restaurantModel;
    private Calendar calendar;
    private Button buttonDate;
    private Button buttonTime;

    private EditText editTextNoOfPersons;

    private boolean isDate;
    private boolean isTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getInt("score");
            restaurantModel = getArguments().getParcelable("restaurantSelected");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_game_result, null);
        initView(view);
        return view;
    }

    public void initView(final View view) {
        calendar = Calendar.getInstance();
        final TextView textResult = (TextView) view.findViewById(R.id.textResult);
        final Button button = (Button) view.findViewById(R.id.resultBtn);
        editTextNoOfPersons = (EditText) view.findViewById(R.id.no_of_persons);
        textResult.setText("Your score is " + " " + score + ". Thanks for playing");
        buttonDate = (Button) view.findViewById(R.id.button_date);
        buttonTime = (Button) view.findViewById(R.id.button_time);


        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int mYear = calendar.get(Calendar.YEAR);
                final int mMonth = calendar.get(Calendar.MONTH);
                final int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, mYear, mMonth, mDay);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.HOUR, 2);
                final int hour = calendar.get(Calendar.HOUR);
                final int minute = calendar.get(Calendar.MINUTE);
                final RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(getActivity(), timeSetListener, hour, minute, true);
                timePickerDialog.setMin(hour, minute);
                timePickerDialog.show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTime && isDate && restaurantModel != null && !TextUtils.isEmpty(editTextNoOfPersons.getText().toString().trim())) {

                    if (Utils.isOnline(getActivity())) {
                        final AsyncAddOrder asyncAddOrder = new AsyncAddOrder();
                        asyncAddOrder.execute(editTextNoOfPersons.getText().toString().trim());
                    } else {
                        Utils.displayNoInternetDialog(getActivity());
                    }
                }
            }
        });

    }


    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            isDate = true;
            buttonDate.setText("" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        }
    };


    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            isTime = true;
            buttonTime.setText("" + hourOfDay + ":" + minute);
            calendar.set(Calendar.HOUR, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
        }
    };


    private class AsyncAddOrder extends AsyncTask<String, Void, Void> {
        private ProgressDialog progressDialog;
        private WSAddOrder wsAddOrder;
        private boolean isError;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utils.showProgressDialog(getActivity(), getString(R.string.loading), true);
        }

        @Override
        protected Void doInBackground(String... params) {
            wsAddOrder = new WSAddOrder(getActivity());
            final String date = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
            wsAddOrder.executeService("" + restaurantModel.getRest_id(), restaurantModel.getRest_name(), restaurantModel.getRmUserId(), params[0], "" + score, date);
            return null;
        }

        @Override
        protected void onPostExecute(Void login) {
            super.onPostExecute(login);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (wsAddOrder != null) {
                isError = wsAddOrder.isError();
                if (isError) {
                    final PreferenceUtils preferenceUtils = new PreferenceUtils(getActivity());
                    final String userName = preferenceUtils.getString(PreferenceUtils.USER_NAME);
                    Utils.sendSMSMessage(restaurantModel.getContactNumber(), "You have received order from " + userName);
                    final Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Utils.displayAlertDialog(getActivity(), "", wsAddOrder.getMessage());
                }
            }
        }
    }


}
