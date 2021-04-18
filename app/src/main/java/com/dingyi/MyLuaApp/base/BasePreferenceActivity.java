package com.dingyi.MyLuaApp.base;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.dingyi.MyLuaApp.R;
import com.dingyi.MyLuaApp.databinding.ActivityPreferenceBinding;

public abstract class BasePreferenceActivity extends AppCompatActivity {

    public abstract int getPreferenceId();

    public static BasePreferenceActivity self;

    protected SharedPreferences preference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self=this;
        ActivityPreferenceBinding binding=ActivityPreferenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarParent.toolbar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contents, PreferenceFragmentCompatImp.getInstance()).commit();

    }

    public abstract boolean onPreferenceTreeClick(Preference preference);

}
