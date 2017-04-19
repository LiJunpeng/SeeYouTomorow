package com.example.louis.seeyoutomorrow;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Louis on 4/17/17.
 */

public class TimePickerFragment extends DialogFragment {
    private Calendar calendar;

    private Activity mActivity;
    private TimePickerDialog.OnTimeSetListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

        // This error will remind you to implement an OnTimeSetListener
        //   in your Activity if you forget
        try {
            mListener = (TimePickerDialog.OnTimeSetListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTimeSetListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //得到日期对象
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(mActivity, mListener, hour, minute, true);
//        return new TimePickerDialog(mActivity, mListener, hour, minute,
//                DateFormat.is24HourFormat(mActivity));
    }
//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        //Toast.makeText(getActivity(), "当前时间设置为： " + hourOfDay + " : " + minute, Toast.LENGTH_LONG).show();
//        //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
//
//
//    }

}
