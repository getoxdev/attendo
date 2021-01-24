package com.attendo.data.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetNotice {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("notice")
    @Expose
    List<NoticeDetails> noticeDetailsList;

    public ResponseGetNotice(String status, List<NoticeDetails> noticeDetailsList) {
        this.status = status;
        this.noticeDetailsList = noticeDetailsList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NoticeDetails> getNoticeDetailsList() {
        return noticeDetailsList;
    }

    public void setNoticeDetailsList(List<NoticeDetails> noticeDetailsList) {
        this.noticeDetailsList = noticeDetailsList;
    }
}
