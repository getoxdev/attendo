package com.attendo.data.model;

import com.google.gson.annotations.SerializedName;

public class Id {

    @SerializedName("reminderId")
    private String id;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
