package com.attendo.data.model;

public class ReminderModel {
    public String time_showing;
    public String label_showing;
    public int uuid;

    public ReminderModel(String time_showing, String label_showing) {
        this.time_showing = time_showing;
        this.label_showing = label_showing;
    }
}
