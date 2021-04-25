package com.dingyi.MyLuaApp.base;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.databinding.ActivityPreferenceBinding;

public abstract class BasePreferenceActivity extends AppCompatActivity {

    public abstract int getPreferenceId();


    protected SharedPreferences mPreference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPreferenceBinding mBinding=ActivityPreferenceBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolbarParent.toolbar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contents, new PreferenceFragmentCompatImp().bindActivity(this)).commit();

    }

    public abstract boolean onPreferenceTreeClick(Preference preference);

}
