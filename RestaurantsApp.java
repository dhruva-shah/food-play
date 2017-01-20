package com.restaurantsapp;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Admin on 25-03-2016.
 */
public class RestaurantsApp extends Application {
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }


    private void initImageLoader() {
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.MAX_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50
                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().build();
        // Initialize ImageLoader with configuration.
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    public DisplayImageOptions getDisplayImageOptions() {
        return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.men_image).showImageForEmptyUri(R.drawable.men_image).showImageOnFail(R.drawable.men_image).cacheInMemory(true).cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY).considerExifParams(true).build();
    }

    //    displayer(new FadeInBitmapDisplayer(250)).
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}
