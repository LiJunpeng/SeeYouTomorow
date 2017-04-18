package com.example.louis.seeyoutomorrow;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    RadioButton rdiNotification, rdiToast;
    Button btnOneTime, btnRepeating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rdiNotification = (RadioButton) findViewById(R.id.rdiNotification);
        rdiToast = (RadioButton) findViewById(R.id.rdiToast);
        btnOneTime = (Button) findViewById(R.id.btnOneTime);
        btnRepeating = (Button) findViewById(R.id.btnRepeating);

        btnOneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new TimePickerFragment();
                dialogFragment.show(getFragmentManager(), "timePicker");
//                if (rdiNotification.isChecked()) {
//                    startAlarm(true, false);
//                } else {
//                    startAlarm(false, false);
//                }
            }
        });

//        btnRepeating.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (rdiNotification.isChecked()) {
//                    startAlarm(true, true);
//                } else {
//                    startAlarm(false, true);
//                }
//            }
//        });
    }

    private void startAlarm(boolean isNotification, boolean isRepeat, Calendar calendar) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

//        if (!isNotification) {
//            myIntent = new Intent(MainActivity.this, AlarmToastReceiver.class);
//            pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
//        } else {
//            myIntent = new Intent(MainActivity.this, AlarmNotificationReceiver.class);
//            pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
//        }
//
//        if (!isRepeat) {  // alarm will start one-time at 3 seconds later
//            manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 3000, pendingIntent);
//        } else {  // alarm will start repeating at 3 seconds later, and repeat after that 3 seconds
//            manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 3000, 3000, pendingIntent);
//        }


//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 22);
//        calendar.set(Calendar.MINUTE, 48);
//        calendar.set(Calendar.SECOND, 30);
//        calendar.set(Calendar.MILLISECOND, 0);

        myIntent = new Intent(MainActivity.this, AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);

    }

}
