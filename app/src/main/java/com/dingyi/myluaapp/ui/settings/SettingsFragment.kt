package com.dingyi.myluaapp.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceGroup
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.getAttributeColor
import com.dingyi.myluaapp.common.kts.loadClass
import com.dingyi.myluaapp.common.kts.startActivity


/**
 * @author: dingyi
 * @date: 2021/9/4 15:55
 * @description:
 **/
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        arguments?.getInt("resId")?.let { addPreferencesFromResource(it) }
        (requireActivity() as AppCompatActivity).let { activity ->
            activity.supportActionBar?.title = preferenceScreen.title
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            tintIcons(preferenceScreen, activity.getAttributeColor(R.attr.theme_hintTextColor))
        }

    }


    override fun onPreferenceTreeClick(_preference: Preference?): Boolean {


        _preference?.let { preference ->
            runSimpleCode(preference.key)
        }

        return super.onPreferenceTreeClick(_preference)
    }

    /**
     * run a simple code (openActivity/openPreference)
     */
    private fun runSimpleCode(code: String) {
        if (code.indexOf("openActivity") != -1) {
            requireActivity().startActivity(
                code.substring("openActivity(".length, code.length - 1).loadClass()
            )
        }
    }

    private fun tintIcons(preference: Preference, color: Int) {
        if (preference is PreferenceGroup) {
            for (i in 0 until preference.preferenceCount) {
                tintIcons(preference.getPreference(i), color)
            }
        } else {
            preference.icon?.let { DrawableCompat.setTint(it, color) }
        }
    }

}

