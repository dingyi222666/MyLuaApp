package com.dingyi.MyLuaApp.ui.activitys;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;

import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.base.BasePreferenceActivity;
import com.dingyi.MyLuaApp.utils.ViewUtils;

public class SettingsActivity extends BasePreferenceActivity {

    @Override
    public int getPreferenceId() {
        return R.xml.settings;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setElevation(ViewUtils.dp2px(this,6));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
