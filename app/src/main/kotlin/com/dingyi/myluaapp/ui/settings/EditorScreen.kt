package com.dingyi.myluaapp.ui.settings

import android.content.Intent
import com.dingyi.myluaapp.R
import de.Maxr1998.modernpreferences.PreferenceScreen
import de.Maxr1998.modernpreferences.helpers.*

class EditorScreen(settingScreen: SettingScreen):SettingScreen by settingScreen {
    override fun create(screen: PreferenceScreen.Builder) = screen.apply {
        titleRes = R.string.settings_editor_category

        categoryHeader("settings_editor_function_category") {
            titleRes = R.string.settings_editor_function_category
        }

        editText("symbol") {
            titleRes = R.string.settings_editor_symbol_bar_category
        }

        categoryHeader("settings_editor_appearance_category") {
            titleRes = R.string.settings_editor_appearance_category
        }

        pref("font_set") {
            titleRes = R.string.settings_editor_font_category
            summaryRes = R.string.settings_editor_font_summary
            icon = iconRes(R.drawable.ic_twotone_translate_24)
            onClick {
                getBindFragment().startActivityForResult(
                    Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        type = "font/ttf"
                    },
                    SettingsFragment.REQUEST_FONT_SET_CODE
                )
                true
            }
        }

        switch("magnifier_set") {
            titleRes = R.string.settings_editor_magnifier_category
            summaryRes = R.string.settings_editor_magnifier_summary
            icon = iconRes(R.drawable.ic_twotone_search_24)

        }

    }
}