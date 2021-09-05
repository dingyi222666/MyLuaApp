package com.dingyi.myluaapp.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceGroup
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.getAttributeColor
import com.dingyi.myluaapp.common.kts.javaClass
import com.dingyi.myluaapp.common.kts.loadClass
import com.dingyi.myluaapp.common.kts.startActivity
import com.dingyi.myluaapp.ui.GeneralActivity


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
            if (preference.hasKey()) {
                runSimpleCode(preference.key)
            }
        }

        return super.onPreferenceTreeClick(_preference)
    }

    /**
     * run a simple code (openActivity/openPreference)
     */
    private fun runSimpleCode(code: String) {

        when {
            code.indexOf("openActivity") != -1 -> {
                requireActivity().startActivity(
                    code.substring(code.indexOf("(")+1, code.length - 1).loadClass()
                )
            }
            code.indexOf("openPreference") != -1 -> {
                val targetFieldName = code.substring(code.indexOf("(")+1, code.length - 1)
                requireActivity().startActivity<GeneralActivity> {
                    putExtra("type", javaClass<SettingsFragment>().name)
                    val targetBundle = Bundle()
                    targetBundle.putInt(
                        "resId",
                        javaClass<R.xml>().getField(targetFieldName).get(null) as Int
                    )
                    putExtra("arg", targetBundle)
                }
            }
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

