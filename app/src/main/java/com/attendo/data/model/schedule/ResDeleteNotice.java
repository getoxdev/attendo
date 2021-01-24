package com.attendo.data.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResDeleteNotice {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("notice")
    @Expose
    List<NoticeDetails> noticeDetailsList;

    public ResDeleteNotice(String status, String message, List<NoticeDetails> noticeDetailsList) {
        this.status = status;
        this.message = message;
        this.noticeDetailsList = noticeDetailsList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NoticeDetails> getNoticeDetailsList() {
        return noticeDetailsList;
    }

    public void setNoticeDetailsList(List<NoticeDetails> noticeDetailsList) {
        this.noticeDetailsList = noticeDetailsList;
    }
}
