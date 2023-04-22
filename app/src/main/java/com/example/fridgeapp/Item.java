package com.example.fridgeapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class Item implements Parcelable {

    private String name;
    private String brand;

    private String date;

    private String category;

    private Date calDate;

    private int month, day, year;


    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(int month, int day, int year)
    {
        date = month + "-" + day + "-" + year;
        calDate = new Date(year, month, day);
        this.month = month;
        this.year = year;
        this.day = day;
    }

    public Item(String name, String category) {
        this.name = name;
        this.category = category;

    }

    public Item(String name, String brand, String category) {
        this.name = name;
        this.brand = brand;
        this.category = category;
    }

    public Item(String name,String category, int month, int day, int year)
    {
        this.name = name;
        this.category = category;
        date = month + "-" + day + "-" + year;
        calDate = new Date(year, month, day);
        this.month = month;
        this.year = year;
        this.day = day;

    }

    public Item(String name, String brand, int month, int day, int year, String category) {
        this.name = name;
        this.brand = brand;
        this.date = month + "-" + day + "-" + year;
        this.category = category;
        calDate = new Date(year, month, day);
        this.month = month;
        this.year = year;
        this.day = day;

    }

    protected Item(Parcel in) {
        name = in.readString();
        brand = in.readString();
        date = in.readString();
        category = in.readString();
        calDate = new Date(in.readLong());
        month = in.readInt();
        day = in.readInt();
        year = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public Date getCalDate() {
        return calDate;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getCategoryIndex()
    {
        switch(category)
        {
            case "Other":
                return 0;
            case "Dairy":
                return 1;
            case "Produce":
                return 2;
            case "Meats":
                return 3;
            case "Beverages":
                return 4;
            default:
                return 0;
        }

    }


    public String getBrand() {
        return brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setYear(int year) {
        this.year = year;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(brand);
        dest.writeString(date);
        dest.writeString(category);
        if (date != null) {
            long time = calDate.getTime();
            dest.writeLong(time);
            dest.writeInt(month);
            dest.writeInt(day);
            dest.writeInt(year);
        }

    }

    public static Comparator<Item> ItemDateComparator = new Comparator<Item>() {
        @Override
        public int compare(Item i1, Item i2)
        {
            if (i1.getCalDate() == null || i2.getCalDate() == null)
                return 0;
            return i1.getCalDate().compareTo(i2.getCalDate());
        }

    };

    public static Comparator<Item> ItemCategoryComparator = new Comparator<Item>() {
        @Override
        public int compare(Item i1, Item i2)
        {
            return i1.getCategory().compareTo(i2.getCategory());
        }
    };

    public static Comparator<Item> ItemNameComparator = new Comparator<Item>() {
        @Override
        public int compare(Item i1, Item i2)
        {
            String name1 = i1.getName().toLowerCase();
            String name2 = i2.getName().toLowerCase();
            return name1.compareTo(name2);
        }
    };

}
