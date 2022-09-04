package com.example.foodie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ResturantModel implements Parcelable {
    private String name;
    private String address;
    private String image;
    private float delivery_charge;
    private Hours hours;
    private List<Menus> menus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(float delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public List<Menus> getMenus() {
        return menus;
    }

    public void setMenus(List<Menus> menus) {
        this.menus = menus;
    }

    protected ResturantModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        image = in.readString();
        delivery_charge = in.readFloat();
        menus = in.createTypedArrayList(Menus.CREATOR);
    }

    public static final Creator<ResturantModel> CREATOR = new Creator<ResturantModel>() {
        @Override
        public ResturantModel createFromParcel(Parcel in) {
            return new ResturantModel(in);
        }

        @Override
        public ResturantModel[] newArray(int size) {
            return new ResturantModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(image);
        parcel.writeFloat(delivery_charge);
        parcel.writeTypedList(menus);
    }
}

