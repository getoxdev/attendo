package com.attendo.data.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUser {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("user")
    @Expose
    private UserDetails userDetails;

    public ResponseUser(String status, UserDetails userDetails) {
        this.status = status;
        this.userDetails = userDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
