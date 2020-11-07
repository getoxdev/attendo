package com.attendo.data.model;

import com.google.gson.annotations.SerializedName;

public class Id
{

    @SerializedName("id")
    private String id;

    public Id(String id)
    {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
