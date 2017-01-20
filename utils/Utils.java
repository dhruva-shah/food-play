package com.restaurantsapp.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.restaurantsapp.R;
import com.restaurantsapp.activity.LoginActivity;

import java.io.File;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

/**
 * @purpose commonly used functions
 * @purpose
 */
public class Utils {

    public static final int IS_LOGIN_FROM_REQUEST = 0;
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static final String GCM_PARAMS_MESSAGE = "message";
    public static final String IS_LOCATION = "is_location";
    public static final String GCM_PARAMS_APPROVAL_CODE = "approval_code";
    public static final String GCM_PARAMS_FIXED_LOCATION = "fixed_location";
    public static final String CRITTERCISM_APP_ID = "cadb5fbc78784dd2bfdc1f5d8c98159500555300";

    /**
     * @param activity
     * @purpose hide softkey board
     */
    public static void hideSoftKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     //     * get string from response
     //     *
     //     * @param result
     //     * @return String
     //     */
//    public static String getStringFromResponse(Response result) {
//        BufferedReader reader = null;
//        StringBuilder sb = new StringBuilder();
//        try {
//            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
//
//            String line;
//
//            try {
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String message = sb.toString();
//        return message;
//    }

    /**
     * @param mActivity
     * @param message
     * @param isCancelable
     * @return
     * @purpose show progress dialog
     */
    public static ProgressDialog showProgressDialog(final Activity mActivity, final String message, boolean isCancelable) {

        final ProgressDialog mDialog = new ProgressDialog(mActivity);
        mDialog.show();
        mDialog.setCancelable(isCancelable);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(message);
        return mDialog;
    }

    /**
     * @param context
     * @param title
     * @param msg
     * @param strPositiveText
     * @param strNegativeText
     * @param isNagativeBtn
     * @param isFinish
     * @purpose dialog which show positive and optional negative button
     */
    public static void displayDialog(final Activity context, final String title, final String msg, final String strPositiveText, final String strNegativeText, final boolean isNagativeBtn, final boolean isFinish) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setMessage(msg);
        dialog.setPositiveButton(strPositiveText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (msg != null) {
                    /*if (msg.equals(context.getString(R.string.event_updated_successfully)))
                    {
						context.getFragmentManager().popBackStack(CalenderEventDetailFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
					}
					else
					{*/

                    if (isFinish) {
                        context.onBackPressed();
                    }
                    //					}
                }
            }
        });
        if (isNagativeBtn) {
            dialog.setNegativeButton(strNegativeText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    /**
     * @param context
     * @param title
     * @param msg
     * @param strPositiveText
     * @param strNegativeText
     * @param isNagativeBtn
     * @param isFinish
     * @purpose dialog which show positive and optional negative button
     */
    public static void displayAlertDialogActivity(final Activity context, final String title, final String msg, final String strPositiveText, final String strNegativeText, final boolean isNagativeBtn, final boolean isFinish) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setMessage(msg);
        dialog.setPositiveButton(strPositiveText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (msg != null) {
                    /*if (msg.equals(context.getString(R.string.event_updated_successfully)))
                    {
						context.getFragmentManager().popBackStack(CalenderEventDetailFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
					}
					else
					{*/

                    if (isFinish) {
                        context.onBackPressed();
                    }
                    //					}
                }
            }
        });
        AlertDialog alertDialog = dialog.create();
        if (isNagativeBtn) {
            dialog.setNegativeButton(strNegativeText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        }
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

//Show the dialog!
        alertDialog.show();

//Set the dialog to immersive
        alertDialog.getWindow().getDecorView().setSystemUiVisibility(
                context.getWindow().getDecorView().getSystemUiVisibility());

//Clear the not focusable flag from the window
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    /**
     * @param context
     * @param message
     * @return
     * @description use to check internet network connection if network
     * connection not available than alert for open network
     * settings
     */
    public static boolean isOnline(final Activity context, boolean message) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        if (message) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(context.getString(R.string.app_name));
            dialog.setCancelable(false);
            dialog.setMessage("");
            dialog.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    //                    context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                }
            });
            dialog.setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            dialog.show();

            return false;
        }
        return false;
    }

    /**
     * @param context
     * @return
     * @description use to check internet network connection if network
     * connection not available than alert for open network
     * settings
     */
    public static boolean isOnline(final Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function is used for validate email address
     *
     * @param email
     * @return true if email is valid otherwise false
     */
    public static boolean isEmailAddressValid(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    /**
     * Public method to display a Normal Alert Dialog with a single option Button "Ok"
     *
     * @param title   Title to be Set in the AlertDialog
     * @param msg     Appropriate Message to be Set in the AlertDialog
     * @param context Context passed where the Alert Dialog needs to be displayed
     */
    public static void displayAlertDialog(final Context context, final String title, String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.setNeutralButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = alertDialog.create();
        if (!((Activity) context).isFinishing()) {
            if (!dialog.isShowing()) {
                alertDialog.show();
            }
        }
    }

    /**
     * This method find the difference between current date & given date and
     * return the difference in String
     *
     * @param thenDate date
     * @return
     */
    public static String getDateDifference(Date thenDate) {
        if (thenDate != null) {
            final Calendar calNow = Calendar.getInstance();
            final Calendar calThen = Calendar.getInstance();
            calNow.setTime(new Date());
            calThen.setTime(thenDate);

            // Get the represented date in milliseconds
            final long nowMs = calNow.getTimeInMillis();
            final long thenMs = calThen.getTimeInMillis();

            // Calculate difference in milliseconds
            final long diff = nowMs - thenMs;

            // Calculate difference in seconds
            final long diffMinutes = diff / (60 * 1000);
            final long diffHours = diff / (60 * 60 * 1000);
            final long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffMinutes < 60) {
                if (diffMinutes == 1)
                    return diffMinutes + " minute ago";
                else if (diffMinutes == -1) {
                    return "1 minute ago";
                } else
                    return diffMinutes + " minutes ago";
            } else if (diffHours < 24) {
                if (diffHours == 1)
                    return diffHours + " hour ago";
                else
                    return diffHours + " hours ago";
            } else if (diffDays < 30) {
                if (diffDays == 1)
                    return diffDays + " day ago";
                else
                    return diffDays + " days ago";
            } else if (diffDays >= 30) {

                long monthCount = diffDays / 30;

                return monthCount + " months ago";

            } else {
                return getMonth(calThen.get(Calendar.MONTH)) + " " + calThen.get(Calendar.DATE) + " " + calThen.get(Calendar.YEAR);
            }
        } else {
            return "";
        }
    }

    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    /**
     * /**
     * Generate Intent to open camera
     *
     * @param context : {@link Context} to create intent
     * @return : {@link Intent} to open camera.
     */
    public static Intent generateCameraPickerIntent(Context context) {
        File root = null;
        if (Utils.sdCardMounted()) {
            root = new File(Environment.getExternalStorageDirectory() + File.separator + context.getString(R.string.app_name));

            if (!root.exists()) {
                root.mkdirs();
            }
        }

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        return intent;
    }

    /**
     * To find if sd card is mounted or not
     */
    public static boolean sdCardMounted() {
        boolean isMediaMounted = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            isMediaMounted = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_CHECKING.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_NOFS.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_REMOVED.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_SHARED.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_UNMOUNTABLE.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_UNMOUNTED.equals(state)) {
            isMediaMounted = false;
        }
        return isMediaMounted;
    }

    /**
     * Generate intent to open gallery to choose image
     *
     * @param context :{@link Context} to create intent
     * @return : {@link Intent} to open gallery
     */
    public static Intent generateGalleryPickerIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        return intent;
    }

    /**
     * Public method to display a Normal Alert Dialog with a single option Button "Ok"
     *
     * @param isFinish Title to be Set in the AlertDialog
     * @param title    Title to be Set in the AlertDialog
     * @param msg      Appropriate Message to be Set in the AlertDialog
     * @param context  Context passed where the Alert Dialog needs to be displayed
     */
    public static void displayAlertDialogActivity(final boolean isFinish, String title, String msg, final Activity context) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.setNeutralButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (isFinish)
                    context.finish();
            }
        });

        final AlertDialog dialog = alertDialog.create();
        if (!context.isFinishing()) {
            if (!dialog.isShowing()) {
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

//Show the dialog!
                dialog.show();

//Set the dialog to immersive
                dialog.getWindow().getDecorView().setSystemUiVisibility(
                        context.getWindow().getDecorView().getSystemUiVisibility());

//Clear the not focusable flag from the window
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            }
        }
    }


    public static void storeString(final Context context, final String key,
                                   final String value) {
        final SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void storeBoolean(final Context context, final String key,
                                    final Boolean value) {
        final SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void storeInt(final Context context, final String key,
                                final int value) {
        final SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(final Context context, final String key) {
        final SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public static String getString(final Context context, final String key) {
        final SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        return preferences.getString(key, "123456");
    }

    public static boolean getBoolean(final Context context, final String key) {
        final SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }


    public static final void displayNoInternetDialog(final Context context) {
        displayAlertDialog(context, context.getString(R.string.app_name), context.getString(R.string.alert_no_internet));
    }


    public static void displayLogoutAlertDialog(final Context context, final String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("");
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.setNeutralButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                final Intent intent = new Intent(context, LoginActivity.class);
                final PreferenceUtils preferenceUtils = new PreferenceUtils(context);
                preferenceUtils.clearAllPreferenceData();
                context.startActivity(intent);
                ((Activity) context).finish();
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = alertDialog.create();
        if (!((Activity) context).isFinishing()) {
            if (!dialog.isShowing()) {
                alertDialog.show();
            }
        }
    }


    public static void sendSMSMessage(final String contactNumber, final String message) {
        try {
            if (!TextUtils.isEmpty(contactNumber)) {
                final SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(contactNumber, null, message, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
