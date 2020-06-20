package com.example.travel;

import android.app.Application;

import java.util.Map;
//Global data that save user's personal info & status locally
public class GlobalVariable extends Application {
    public boolean isLogin;
    public String documentReference;
    public Map<String, Object> personalInfo;
}