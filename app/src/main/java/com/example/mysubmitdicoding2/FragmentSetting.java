package com.example.mysubmitdicoding2;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetting extends PreferenceFragmentCompat  implements SharedPreferences.OnSharedPreferenceChangeListener {
    public AlarmReceiver alarmReceiver;
    public SharedPreferences sharedPreferences;
    String ALARM_DAILY_TIME = "07:00";
    String ALARM_RELEASE_TIME = "08:00";
    private String DAILYTIME;
    private String RELEASETIME;
    public FragmentSetting() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        init();
        alarmReceiver = new AlarmReceiver();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    private void init() {
        DAILYTIME = getResources().getString(R.string.setting_daily_key);
        RELEASETIME = getResources().getString(R.string.setting_release_key);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(DAILYTIME)) {
            if (sharedPreferences.getBoolean(getResources().getString(R.string.setting_daily_key),false)) {
                alarmReceiver.setRepeatingAlarm(this.requireContext(), AlarmReceiver.TYPE_DAILYREMINDER, ALARM_DAILY_TIME, getResources().getString(R.string.alarm_message_daily));
            }
            else
            {
                alarmReceiver.cancelAlarm(this.requireContext(),AlarmReceiver.TYPE_DAILYREMINDER);
            }
        }
        else
        {//release
            if (sharedPreferences.getBoolean(getResources().getString(R.string.setting_release_key),false)) {
                alarmReceiver.setRepeatingAlarm(this.requireContext(), AlarmReceiver.TYPE_RELEASEREMINDER, ALARM_RELEASE_TIME, getResources().getString(R.string.alarm_message_release));
            }
            else
            {
                alarmReceiver.cancelAlarm(this.requireContext(),AlarmReceiver.TYPE_RELEASEREMINDER);
            }
        }
        }



}
