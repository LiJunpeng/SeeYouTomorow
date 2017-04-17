package com.example.louis.seeyoutomorrow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Louis on 4/16/17.
 */

public class AlarmToastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "This is my alarm", Toast.LENGTH_LONG).show();
    }
}
