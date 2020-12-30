package com.attendo.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class schedule_list {

    private Classes classes;
    private String _id;

    @SerializedName("class")
    @Expose
    private String _class;

    private String __v;


    public schedule_list(Classes classes, String _id, String _class, String __v) {
        this.classes = classes;
        this._id = _id;
        this._class = _class;
        this.__v = __v;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
