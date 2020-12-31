package com.attendo.data.model;

import com.google.firebase.storage.StorageException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseJoinClass {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("class")
    @Expose
    private Class _class;

    @SerializedName("message")
    @Expose
    private String message;

    public ResponseJoinClass(String status, Class _class, String message) {
        this.status = status;
        this._class = _class;
        this.message = message;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
