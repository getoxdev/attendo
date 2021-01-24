package com.attendo.data.model.schedule;

import com.attendo.data.model.schedule.Class;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCreateClass {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("class")
    @Expose
    private Class _class;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Class get_class() {
        return _class;
    }

    public void set_class(Class _class) {
        this._class = _class;
    }
}
