package com.restaurantsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.restaurantsapp.R;
import com.restaurantsapp.RestaurantsApp;
import com.restaurantsapp.model.GalleryModel;

import java.util.ArrayList;

/**
 * Created by Admin on 29-03-2016.
 */
public class BannerListAdapter extends RecyclerView.Adapter<BannerListAdapter.ViewHolder> {

    private ArrayList<GalleryModel> galleryModelArrayList;
    private Context context;
    //    private static int width;
    private RestaurantsApp restaurantsApp;

    public BannerListAdapter(final Context fragment, final ArrayList<GalleryModel> galleryModelArrayList) {
        this.galleryModelArrayList = galleryModelArrayList;
        context = fragment;
//        width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        restaurantsApp = (RestaurantsApp) context.getApplicationContext();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProfilePic;
        public RelativeLayout relMain;
        public TextView textViewName;

        public ViewHolder(View v) {
            super(v);
            imageViewProfilePic = (ImageView) v.findViewById(R.id.row_custom_gallery_image);
            textViewName = (TextView) v.findViewById(R.id.row_custom_gallery_textview_name);
            relMain = (RelativeLayout) v.findViewById(R.id.row_custom_gallery_rel_main);
//            RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams((width / 2), relMain.getHeight());
//            relMain.setLayoutParams(l);
//            relMain.setPadding(5, 5, 5, 5);
        }
    }

    @Override
    public BannerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View v = LayoutInflater.from(context)
                .inflate(R.layout.row_custom_gallery, viewGroup, false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BannerListAdapter.ViewHolder viewHolder, int i) {
        final String url = galleryModelArrayList.get(i).getImagePath();
        if (URLUtil.isValidUrl(url)) {
            restaurantsApp.getImageLoader().displayImage(url, viewHolder.imageViewProfilePic, restaurantsApp.getDisplayImageOptions());
        } else {
            restaurantsApp.getImageLoader().displayImage("file://" + url, viewHolder.imageViewProfilePic, restaurantsApp.getDisplayImageOptions());
        }

    }

    @Override
    public int getItemCount() {
        return galleryModelArrayList.size();
    }
}

