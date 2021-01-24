package com.attendo.data.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseNotice {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("notice")
    @Expose
    NoticeDetails noticeDetails;

    public ResponseNotice(String status, NoticeDetails noticeDetails) {
        this.status = status;
        this.noticeDetails = noticeDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NoticeDetails getNoticeDetails() {
        return noticeDetails;
    }

    public void setNoticeDetails(NoticeDetails noticeDetails) {
        this.noticeDetails = noticeDetails;
    }
}
