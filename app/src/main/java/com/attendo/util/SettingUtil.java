package com.attendo.util;

import android.content.Context;
import android.provider.Settings;

public class SettingUtil {
    public boolean isTimeAutomatic(Context context) {
        return Settings.Global.getInt(
                context.getContentResolver()
                , Settings.Global.AUTO_TIME,
                0
        ) == 1;
    }
}
