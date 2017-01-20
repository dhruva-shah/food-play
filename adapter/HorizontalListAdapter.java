package com.restaurantsapp.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.restaurantsapp.R;
import com.restaurantsapp.RestaurantsApp;
import com.restaurantsapp.webservice.WSConstant;

import java.util.ArrayList;

/**
 * Created by Admin on 29-03-2016.
 */
public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder> {

    private ArrayList<String> arrayList;
    private Context context;
    //    private static int width;
    private RestaurantsApp restaurantsApp;

    public HorizontalListAdapter(final Context fragment, final ArrayList<String> arrayList) {
        this.arrayList = arrayList;
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
    public HorizontalListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View v = LayoutInflater.from(context)
                .inflate(R.layout.row_custom_gallery, viewGroup, false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HorizontalListAdapter.ViewHolder viewHolder, int i) {
        restaurantsApp.getImageLoader().displayImage(arrayList.get(i).toString(), viewHolder.imageViewProfilePic, restaurantsApp.getDisplayImageOptions());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

