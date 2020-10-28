package com.attendo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.attendo.R;

public class CustomLoadingDialog {

    Activity activity;
    AlertDialog alertDialog;
    TextView loadingText;

    public CustomLoadingDialog(Activity activity) {
        this.activity = activity;

    }

    public void startDialog(Boolean value){
        AlertDialog.Builder builder =  new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(value);



        View view = activity.getWindow().getDecorView();
        view.setBackgroundResource(android.R.color.transparent);
        alertDialog = builder.create();
        alertDialog.show();


    }



    public void dismissDialog(){
        alertDialog.dismiss();
    }




}
