package com.attendo.data.model.reminder;

import com.attendo.data.model.reminder.Reminder;
import com.google.gson.annotations.SerializedName;

public class Response
{
    @SerializedName("status")
    private String status;

    @SerializedName("reminder")
    private Reminder reminder;

    public Response(String status, Reminder reminder)
    {
        this.status = status;
        this.reminder = reminder;
    }
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }
}
