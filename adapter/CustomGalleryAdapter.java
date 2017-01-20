/**
 *
 */
package com.restaurantsapp.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.restaurantsapp.R;
import com.restaurantsapp.RestaurantsApp;
import com.restaurantsapp.model.GalleryModel;

/**
 * Purpose:This class is use for set Photos in to custom gallery gridview.
 *
 * @author Vandit Patel
 * @version 1.0
 * @date 01/05/14
 */
public class CustomGalleryAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<GalleryModel> allImagesList;
    private int width;
    private RestaurantsApp restaurantsApp;

    /**
     * parameterized constructor
     *
     * @param mImageList - defines object of type ListData which stores detail of
     *                   gallery images
     * @param id         - define which view is selected
     */
    @SuppressWarnings("deprecation")
    public CustomGalleryAdapter(Context mContext, ArrayList<GalleryModel> mImageList) {
        super();
        this.mContext = mContext;
        this.allImagesList = mImageList;
        width = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
        restaurantsApp = (RestaurantsApp) mContext.getApplicationContext();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return allImagesList.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        return allImagesList.get(position);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_custom_gallery, null);
            holder = new Holder();
            holder.imgView = (ImageView) convertView.findViewById(R.id.row_custom_gallery_image);
            holder.imgViewSelected = (ImageView) convertView.findViewById(R.id.row_custom_gallery_image_selected);
            holder.relMain = (RelativeLayout) convertView.findViewById(R.id.row_custom_gallery_rel_main);
            RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams((width / 2), (width / 3));
            holder.relMain.setLayoutParams(l);
            holder.relMain.setPadding(5, 5, 5, 5);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
            ((Holder) convertView.getTag()).imgView.setTag(position);
        }
        holder.imgView.setTag(position);
        final GalleryModel imgPath = allImagesList.get(position);
        holder.imgView.setTag("file://" + imgPath.getImagePath());
        restaurantsApp.getImageLoader()
                .displayImage(holder.imgView.getTag().toString(), holder.imgView, restaurantsApp.getDisplayImageOptions());

        if (imgPath.isSelected()) {
            holder.imgViewSelected.setVisibility(View.VISIBLE);
        } else {
            holder.imgViewSelected.setVisibility(View.GONE);
        }

        // Handle click event of image layout
        return convertView;
    }

    private class Holder {
        private ImageView imgView;
        private ImageView imgViewSelected;
        private RelativeLayout relMain;
    }

}
