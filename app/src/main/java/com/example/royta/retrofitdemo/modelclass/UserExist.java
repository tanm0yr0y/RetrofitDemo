package com.example.royta.retrofitdemo.ModelClass;

import com.google.gson.annotations.SerializedName;
public class UserExist {
    @SerializedName("isSend")
    private boolean isSend;
    @SerializedName("code")
    private int code;
    @SerializedName("email")
    private boolean email;

    public UserExist(boolean email, boolean isSend, int code) {
        this.email = email;
        this.isSend = isSend;
        this.code = code;
    }

    public boolean isSend() {
        return isSend;
    }

    public boolean isEmailExist() {
        return email;
    }

    public int getCode() {
        return code;
    }
}
