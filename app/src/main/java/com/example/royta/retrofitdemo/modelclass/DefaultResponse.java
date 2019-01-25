package com.example.royta.retrofitdemo.ModelClass;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("error")     //there are two option - we can keep the variable name same as the Json object key name or
    private boolean err;        //if we want a different name we can use SerializedName annotation

    @SerializedName("message")
    private String msg;

    public DefaultResponse(boolean err, String msg) {
        this.err = err;
        this.msg = msg;
    }

    public boolean isErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }
}

