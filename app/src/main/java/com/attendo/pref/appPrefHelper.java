package com.attendo.pref;

public interface appPrefHelper {
    void savetoken(String token);
    void timeStamp(String timeStamp);

    Boolean removeToken();
    Boolean removetimeStamp();

    String getToken();
    String gettimeStamp();

}
