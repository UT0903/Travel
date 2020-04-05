package com.example.travel;

public class TripInfo {
    public String title, travel_code_name, start_date, end_date, travel_code;
    public int lower_bound, upper_bound, price;
    public int image;
    public TripInfo(){}
    public TripInfo(String title, String travel_code, String start_date,
                    String end_date, String lower_bound, String upper_bound, String price) {
        this.title = title;
        this.travel_code_name = this.travel_code_name;
        this.travel_code = travel_code;
        this.start_date = start_date;
        this.end_date = end_date;
        this.lower_bound = Integer.parseInt(lower_bound);
        this.upper_bound = Integer.parseInt(upper_bound);
        this.price = Integer.parseInt(price);
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

    public int getLower_bound() {
        return lower_bound;
    }

    public void setLower_bound(int lower_bound) {
        this.lower_bound = lower_bound;
    }

    public int getUpper_bound() {
        return upper_bound;
    }

    public void setUpper_bound(int upper_bound) {
        this.upper_bound = upper_bound;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
