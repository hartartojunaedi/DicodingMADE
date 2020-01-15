package com.example.mysubmitdicoding2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        FragmentSetting fragment = new FragmentSetting();
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }
}
