package com.example.louis.seeyoutomorrow;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final int RequestPermissionCode = 1;

    RadioButton rdiNotification, rdiToast;
    Button btnOneTime, btnRepeating;
    private boolean isRecording;

    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    MediaPlayer mediaPlayer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isRecording = false;
        rdiNotification = (RadioButton) findViewById(R.id.rdiNotification);
        rdiToast = (RadioButton) findViewById(R.id.rdiToast);
        btnOneTime = (Button) findViewById(R.id.btnOneTime);
        btnRepeating = (Button) findViewById(R.id.btnRepeating);

        btnOneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pick time =======================
                //DialogFragment dialogFragment = new TimePickerFragment();
                //dialogFragment.show(getFragmentManager(), "timePicker");

                // Record sound =====================

                if(checkPermission()) {

                    if (!isRecording) {

                        AudioSavePathInDevice =
                                Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                        "AudioRecording.3gp";

                        MediaRecorderReady();

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        isRecording = true;

                        Toast.makeText(MainActivity.this, "Recording started",
                                Toast.LENGTH_LONG).show();
                    } else {
                        mediaRecorder.stop();
                        Toast.makeText(MainActivity.this, "Recording Completed",
                                Toast.LENGTH_LONG).show();
                        isRecording = false;

                        DialogFragment dialogFragment = new TimePickerFragment();
                        dialogFragment.show(getFragmentManager(), "timePicker");
                    }

                } else {
                    requestPermission();
                }

            }
        });

        btnRepeating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException  {

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Recording Playing",
                        Toast.LENGTH_LONG).show();

            }
        });
    }

    private void startAlarm(boolean isNotification, boolean isRepeat, int hour, int minute) {
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


        

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        myIntent = new Intent(MainActivity.this, AlarmNotificationReceiver.class);
        myIntent.putExtra("AUDIO_PATH", AudioSavePathInDevice);
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);

    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(this, "当前时间设置为： " + hourOfDay + " : " + minute, Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        calendar.set(Calendar.MINUTE, minute);

        startAlarm(true, true, hourOfDay, minute);
    }


    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public void MediaRecorderReady(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }


}
