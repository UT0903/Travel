package com.example.travel.searchTrip;

import java.io.Serializable;
//Prototype of class TripInfo
public class TripInfo implements Serializable {
    private String title, travel_code_name, start_date, end_date, travel_code;
    private String lower_bound, upper_bound, price;
    private int image;
    public TripInfo(){}
    public TripInfo(String title, String travel_code, String start_date,
                    String end_date, String lower_bound, String upper_bound, String price, int image) {
        this.title = title;
        this.travel_code_name = this.travel_code_name;
        this.travel_code = travel_code;
        this.start_date = start_date;
        this.end_date = end_date;
        this.lower_bound = lower_bound;
        this.upper_bound = upper_bound;
        this.price = price;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTravel_code_name() {
        return travel_code_name;
    }

    public void setTravel_code_name(String travel_code_name) {
        this.travel_code_name = travel_code_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTravel_code() {
        return travel_code;
    }

    public void setTravel_code(String travel_code) {
        this.travel_code = travel_code;
    }

    public String getLower_bound() {
        return lower_bound;
    }

    public void setLower_bound(String lower_bound) {
        this.lower_bound = lower_bound;
    }

    public String getUpper_bound() {
        return upper_bound;
    }

    public void setUpper_bound(String upper_bound) {
        this.upper_bound = upper_bound;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
