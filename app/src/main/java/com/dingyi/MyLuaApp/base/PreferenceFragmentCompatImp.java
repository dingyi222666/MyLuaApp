package com.dingyi.MyLuaApp.base;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class PreferenceFragmentCompatImp extends PreferenceFragmentCompat {

    private BasePreferenceActivity mBasePreferenceActivity;


    public PreferenceFragmentCompatImp bindActivity(BasePreferenceActivity activity) {
        mBasePreferenceActivity = activity;
        return this;
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        this.addPreferencesFromResource(mBasePreferenceActivity.getPreferenceId());
        mBasePreferenceActivity.mPreference = getPreferenceManager().getSharedPreferences();

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        mBasePreferenceActivity.onPreferenceTreeClick(preference);
        return super.onPreferenceTreeClick(preference);
    }
}