package com.restaurantsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by indianic on 10/02/16.
 */


public class RestaurantModel implements Parcelable {
    private String rest_name, rest_banner1;
    private int rest_id;
    private String rmUserId;
    private String contactNumber;

    private ArrayList<String> restImageUrl;
    private ArrayList<String> restMenuUrl;


    public void setRestImageUrl(ArrayList<String> restImageUrl) {
        this.restImageUrl = restImageUrl;
    }

    public ArrayList<String> getRestImageUrl() {
        return restImageUrl;
    }

    public ArrayList<String> getRestMenuUrl() {
        return restMenuUrl;
    }

    public void setRestMenuUrl(ArrayList<String> restMenuUrl) {
        this.restMenuUrl = restMenuUrl;
    }


    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getRest_banner1() {
        return rest_banner1;
    }

    public void setRest_banner1(String rest_banner1) {
        this.rest_banner1 = rest_banner1;
    }

    public String getRmUserId() {
        return rmUserId;
    }

    public void setRmUserId(String rmUserId) {
        this.rmUserId = rmUserId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public RestaurantModel(int rest_id, String rest_name, String rest_banner1, ArrayList restImageUrl, ArrayList restMenuUrl, String rmUserId, final String contactNumber) {
        this.rest_id = rest_id;
        this.rest_name = rest_name;
        this.rest_banner1 = rest_banner1;
        this.restImageUrl = restImageUrl;
        this.restMenuUrl = restMenuUrl;
        this.rmUserId = rmUserId;
        this.contactNumber = contactNumber;

    }


    protected RestaurantModel(Parcel in) {
        rest_id = in.readInt();
        if (in.readByte() == 0x01) {
            restImageUrl = new ArrayList<String>();
            in.readList(restImageUrl, String.class.getClassLoader());
        } else {
            restImageUrl = null;
        }
        if (in.readByte() == 0x01) {
            restMenuUrl = new ArrayList<String>();
            in.readList(restMenuUrl, String.class.getClassLoader());
        } else {
            restMenuUrl = null;
        }

        rmUserId = in.readString();
        contactNumber = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rest_id);
        if (restImageUrl == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(restImageUrl);
        }
        if (restMenuUrl == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(restMenuUrl);
        }
        dest.writeString(rmUserId);
        dest.writeString(contactNumber);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RestaurantModel> CREATOR = new Parcelable.Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };
}

