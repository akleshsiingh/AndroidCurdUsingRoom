package com.androidfizz.androidcurdroompersistence;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by Aklesh on 3/9/2018.
 */

public class Utils {



    public static Dialog addPersonDialog(Context context, View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(view);
        Dialog mDialog = mBuilder.create();
       return mDialog;

    }
}
