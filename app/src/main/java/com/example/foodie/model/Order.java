package com.example.foodie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Order implements Parcelable{
    private int ID;
    private String OrderDate;
    private String ResturantName;
    private String ResturantAddress;
    private String Name;
    private String Address;
    private String City;
    private String State;
    private String Pincode;
    private String OrderType;
    private float TotalPrice;
    private String OrderItems;
    private String image;
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Order() {}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        this.OrderDate = orderDate;
    }

    public String getResturantName() {
        return ResturantName;
    }

    public void setResturantName(String resturantName) {
        this.ResturantName = resturantName;
    }

    public String getResturantAddress() {
        return ResturantAddress;
    }

    public void setResturantAddress(String resturantAddress) {
        this.ResturantAddress = resturantAddress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        this.State = state;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        this.Pincode = pincode;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        this.OrderType = orderType;
    }

    public float getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.TotalPrice = totalPrice;
    }

    public String getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(String orderItems) {
        this.OrderItems = orderItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
