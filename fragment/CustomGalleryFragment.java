package com.restaurantsapp.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.restaurantsapp.R;
import com.restaurantsapp.activity.MainActivity;
import com.restaurantsapp.adapter.CustomGalleryAdapter;
import com.restaurantsapp.model.GalleryModel;
import com.restaurantsapp.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CustomGalleryFragment extends MainFragment implements OnItemClickListener {

    private GridView galleryImagesGridView;
    private Button buttonDone;
    private ArrayList<GalleryModel> galleryAllImagesList;
    private Uri mFinalImageUri;
    // private ProgressBar progressBar;
    private GetGalleryImagesDataTask galleryImagesDataTask;
    private static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private Uri mCropImagedUri;
    private File tmpFile;
    private CustomGalleryAdapter customGalleryAdapter;

    private HashMap<String, GalleryModel> hashMap;

    public CustomGalleryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_gallery, null);
    }

    @Override
    public void initView(View view) {
        hashMap = new HashMap<>();
        galleryImagesGridView = (GridView) view.findViewById(R.id.fragment_custom_gallery_gridview);
        buttonDone = (Button) view.findViewById(R.id.fragment_custom_gallery_button_done);
        // progressBar = (ProgressBar) view.findViewById(R.id.fragment_custom_gallery_progressbar);
        galleryImagesGridView.setOnItemClickListener(this);
        callGalleryImagesDataTask();


        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (galleryAllImagesList != null && !galleryAllImagesList.isEmpty()) {
                    final ArrayList<GalleryModel> arrayList = new ArrayList<GalleryModel>();
                    for (String key : hashMap.keySet()) {
                        final GalleryModel galleryModel = hashMap.get(key);
                        arrayList.add(galleryModel);

                    }
                    if (arrayList != null && !arrayList.isEmpty()) {

                        if (getTargetFragment() instanceof AddRestaurantFragment) {
                            final AddRestaurantFragment addRestaurantFragment = (AddRestaurantFragment) getTargetFragment();
                            addRestaurantFragment.addPhoto(getTargetRequestCode(), arrayList);
                        }
                        if (getTargetFragment() instanceof UpdateRestaurantFragment) {
                            final UpdateRestaurantFragment addRestaurantFragment = (UpdateRestaurantFragment) getTargetFragment();
                            addRestaurantFragment.addPhoto(getTargetRequestCode(), arrayList);
                        }
                        getFragmentManager().popBackStack();

                    } else {
                        Utils.displayAlertDialog(getActivity(), "", getString(R.string.please_select_one_photo));
                    }
                }
            }
        });


    }

    /**
     * Call Gallery images data task
     **/
    public void callGalleryImagesDataTask() {
        galleryImagesDataTask = new GetGalleryImagesDataTask();
        galleryImagesDataTask.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final GalleryModel galleryModel = (GalleryModel) parent.getItemAtPosition(position);
        if (galleryModel != null) {
            galleryModel.setSelected(!galleryModel.isSelected());
            customGalleryAdapter.notifyDataSetChanged();
            if (galleryModel.isSelected()) {
                if (hashMap != null && hashMap.size() < 5) {
                    hashMap.put("" + position, galleryModel);
                } else {
                    Utils.displayAlertDialog(getActivity(), "", getString(R.string.you_can_select_images));
                }
            } else {
                hashMap.remove("" + position);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CROP_IMAGE) {
                // uploadImage();
//                final AnalyzingColorDialog mProgressDialog = new AnalyzingColorDialog(tmpFile, this);
//                mProgressDialog.show(getFragmentManager(), mProgressDialog.getClass().getSimpleName());
            }
        }
    }

// private void uploadImage() {
// // final ProgressDialog mProgressDialog = Utils.showProgressDialog(getActivity(), "", getString(R.string.please_wait));
// final AnalyzingColorDialog mProgressDialog = new AnalyzingColorDialog(tmpFile, this);
// mProgressDialog.show(getFragmentManager(), mProgressDialog.getClass().getSimpleName());
// final String baseURL = getString(R.string.base_url) + "/v2/user/profiles/from-upload";
// final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
// builder.addTextBody("profile_type_id", String.format("%d", 1));
// builder.addPart("image", new FileBody(tmpFile));
// final VolleyRequest volleyRequest = new VolleyRequest(Method.POST, baseURL, new VolleyOnResponseListener() {
//
// @Override
// public void onSuccess(String response, String message, int status, boolean success) {
// mProgressDialog.dismiss();
// parseResponse(response, message, status, success);
// }
//
// @Override
// public void onFailure(String error) {
// mProgressDialog.dismiss();
// Utils.showAlertDialog(getActivity(), getString(R.string.app_name), error);
// }
// });
// volleyRequest.setEntity(builder.build());
// VolleyHelper.getInstance().getRequestQueue().add(volleyRequest);
// }
//
// private void parseResponse(String response, String message, int status, boolean success) {
// if (status == 200 && success) {
// try {
// JSONObject jsMain = new JSONObject(response);
// if (jsMain.has(getString(R.string.data))) {
// final Gson gson = new Gson();
// final JSONObject jsonObject = jsMain.getJSONObject(getString(R.string.data));
// final JSONObject jsObject = jsonObject.getJSONObject(getString(R.string.profile));
// final UserProfileModel imageProfileModel = gson.fromJson(jsObject.toString(), UserProfileModel.class);
// Utils.e("" + imageProfileModel.getProfileTypeId());
// final JSONObject jsonObjectException = jsonObject.getJSONObject("exceptions");
// final double accuracy = jsonObjectException.getDouble("accuracy");
// final String text = jsonObjectException.getString("text");
// final String title = jsonObjectException.getString("title");
// final String type = jsonObjectException.getString("type");
// if (type.equalsIgnoreCase("noncritical") || accuracy == 1) {
// // "% \nAccuracy"
// Utils.showAccuracyAlertDialog(getActivity(), "" + accuracy * 100, text);
// final String userModelString = gson.toJson(imageProfileModel);
// Utils.storeString(getActivity(), getString(R.string.pref_key_user_profile_model), userModelString);
// MainActivity.getInstance().setUserProfileModel(imageProfileModel);
// PlumPerfectApplication.getInstance().trackEvent("Photo Upload", null);
// PlumPerfectApplication.getInstance().trackEvent("Photo Upload Via Album", null);
// getFragmentTransaction().hide(this).add(R.id.container, new ColorSignatureFragment(), ColorSignatureFragment.class.getSimpleName())
// .addToBackStack(ColorSignatureFragment.class.getSimpleName()).commit();
// } else {
// Utils.showAccuracyAlertDialog(getActivity(), "" + accuracy * 100, title);
// }
// }
// } catch (Exception e) {
// e.printStackTrace();
// }
//
// }
// }

    /**
     * Call GetGalleryImagesData task and list out all images of sdcard
     */
    private class GetGalleryImagesDataTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressBar.setVisibility(View.VISIBLE);
            mProgressDialog = Utils.showProgressDialog(getActivity(), getString(R.string.loading), true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Cursor mCursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"*"}, null, null,
                    "LOWER(" + MediaStore.Audio.Media.DATE_MODIFIED + ") DESC");
            if (mCursor.getCount() > 0) {
                galleryAllImagesList = new ArrayList<GalleryModel>();
                while (mCursor.moveToNext()) {
                    final GalleryModel galleryModel = new GalleryModel();
                    galleryModel.setImagePath(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                    galleryAllImagesList.add(galleryModel);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            if (isCancelled())
                return;
            // progressBar.setVisibility(View.GONE);
            if (galleryAllImagesList != null && galleryAllImagesList.size() > 0) {
                customGalleryAdapter = new CustomGalleryAdapter(getActivity(), galleryAllImagesList);
                galleryImagesGridView.setAdapter(customGalleryAdapter);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // getActivity().unregisterReceiver(broadcastReceiver);
        if (galleryImagesDataTask != null && galleryImagesDataTask.getStatus() == AsyncTask.Status.RUNNING) {
            galleryImagesDataTask.cancel(true);
        }
    }

    @Override
    public void initActionBar() {
        ((MainActivity) getActivity()).setTopBar(getString(R.string.gallery), false);
    }

    /**
     * Crop the image
     *
     * @return returns <tt>true</tt> if crop supports by the device,otherwise false
     */
    private boolean performCropImage(final String imagePath) {
        try {
            mFinalImageUri = Uri.fromFile(new File(imagePath));
            if (mFinalImageUri != null) {
                // call the standard crop action intent (the user device may not support it)
                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                // indicate image type and Uri
                cropIntent.setDataAndType(mFinalImageUri, "image/*");
                // set crop properties
                cropIntent.putExtra("crop", "true");
                // indicate aspect of desired crop
                // cropIntent.putExtra("aspectX", 1);
                // cropIntent.putExtra("aspectY", 1);
                cropIntent.putExtra("scale", true);
                // indicate output X and Y
                // cropIntent.putExtra("outputX", 500);
                // cropIntent.putExtra("outputY", 500);
                // retrieve data on return
                cropIntent.putExtra("return-data", false);

                tmpFile = createNewFile("CROP_");
                try {
                    tmpFile.createNewFile();
                } catch (IOException ex) {

                }

                mCropImagedUri = Uri.fromFile(tmpFile);
                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImagedUri);
                // start the activity - we handle returning in onActivityResult
                startActivityForResult(cropIntent, REQUEST_CODE_CROP_IMAGE);
                return true;
            }
        } catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return false;
    }

    private File createNewFile(String prefix) {
        if (prefix == null || "".equalsIgnoreCase(prefix)) {
            prefix = "IMG_";
        }
        final File newDirectory = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/");
        if (!newDirectory.exists()) {
            if (newDirectory.mkdir()) {
                Log.e("Tag", newDirectory.getAbsolutePath() + " directory created");
            }
        }
        final File file = new File(newDirectory, (prefix + System.currentTimeMillis() + ".jpg"));
        if (file.exists()) {
            // this wont be executed
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
