package com.dingyi.MyLuaApp.base;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import static com.dingyi.MyLuaApp.base.BasePreferenceActivity.self;

public class PreferenceFragmentCompatImp extends PreferenceFragmentCompat {

    private static PreferenceFragmentCompatImp preferenceFragmentCompatImp=null;


    private PreferenceFragmentCompatImp(){}

    public static PreferenceFragmentCompat getInstance() {
        if (preferenceFragmentCompatImp==null) {
            preferenceFragmentCompatImp=new PreferenceFragmentCompatImp();
        }
        return preferenceFragmentCompatImp;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        this.addPreferencesFromResource(self.getPreferenceId());
        self.preference = getPreferenceManager().getSharedPreferences();

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        self.onPreferenceTreeClick(preference);
        return super.onPreferenceTreeClick(preference);
    }
}