package com.restaurantsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 30-03-2016.
 */
public class OrderModel implements Parcelable {

    private int order_id;
    private int restaurants_id;
    private int user_id;
    private String order_date_time;
    private String restaurants_name;
    private int rm_user_id;
    private int approval_cde;
    private String noOfPersons;
    private String userName;
    private String reservationDateTime;
    private String contactNumber;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getRestaurants_id() {
        return restaurants_id;
    }

    public void setRestaurants_id(int restaurants_id) {
        this.restaurants_id = restaurants_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrder_date_time() {
        return order_date_time;
    }

    public void setOrder_date_time(String order_date_time) {
        this.order_date_time = order_date_time;
    }

    public String getRestaurants_name() {
        return restaurants_name;
    }

    public void setRestaurants_name(String restaurants_name) {
        this.restaurants_name = restaurants_name;
    }

    public int getRm_user_id() {
        return rm_user_id;
    }

    public void setRm_user_id(int rm_user_id) {
        this.rm_user_id = rm_user_id;
    }

    public int getApproval_cde() {
        return approval_cde;
    }

    public void setApproval_cde(int approval_cde) {
        this.approval_cde = approval_cde;
    }


    public String getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(String noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(String reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public OrderModel() {

    }


    protected OrderModel(Parcel in) {
        order_id = in.readInt();
        restaurants_id = in.readInt();
        user_id = in.readInt();
        order_date_time = in.readString();
        restaurants_name = in.readString();
        rm_user_id = in.readInt();
        approval_cde = in.readInt();
        noOfPersons = in.readString();
        userName = in.readString();
        reservationDateTime = in.readString();
        contactNumber = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order_id);
        dest.writeInt(restaurants_id);
        dest.writeInt(user_id);
        dest.writeString(order_date_time);
        dest.writeString(restaurants_name);
        dest.writeInt(rm_user_id);
        dest.writeInt(approval_cde);
        dest.writeString(noOfPersons);
        dest.writeString(userName);
        dest.writeString(reservationDateTime);
        dest.writeString(contactNumber);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OrderModel> CREATOR = new Parcelable.Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };
}