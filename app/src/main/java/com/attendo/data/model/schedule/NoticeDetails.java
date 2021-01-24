package com.attendo.data.model.schedule;

public class NoticeDetails {

    String _id;
    String title;
    String body;
    String class_id;
    String createdAt;
    String updatedAt;
    String _v;

    public NoticeDetails(String _id, String title, String body, String class_id, String createdAt, String updatedAt, String _v) {
        this._id = _id;
        this.title = title;
        this.body = body;
        this.class_id = class_id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this._v = _v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String get_v() {
        return _v;
    }

    public void set_v(String _v) {
        this._v = _v;
    }
}
