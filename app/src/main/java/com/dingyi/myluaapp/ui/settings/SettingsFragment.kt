package com.dingyi.myluaapp.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

/**
 * @author: dingyi
 * @date: 2021/9/4 15:55
 * @description:
 **/
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        arguments?.getInt("resId")?.let { addPreferencesFromResource(it) }

    }
}