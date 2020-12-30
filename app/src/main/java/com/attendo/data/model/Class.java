package com.attendo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class {

    @SerializedName("_id")
    @Expose
    private String _id; //this is the class id

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("cr")
    @Expose
    private String cr;

    @SerializedName("__v")
    @Expose
    private Integer v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
