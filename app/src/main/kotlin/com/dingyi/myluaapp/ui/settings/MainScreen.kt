package com.dingyi.myluaapp.ui.settings

import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.startActivity
import com.dingyi.myluaapp.ui.GeneralActivity
import com.dingyi.myluaapp.ui.about.AboutFragment
import de.Maxr1998.modernpreferences.PreferenceScreen
import de.Maxr1998.modernpreferences.helpers.onClick
import de.Maxr1998.modernpreferences.helpers.pref

class MainScreen(settingsScreen: SettingScreen) : SettingScreen by settingsScreen {


    override fun create(screen: PreferenceScreen.Builder): PreferenceScreen.Builder = screen.apply {
        titleRes = R.string.settings_main_title

        //application
        pref("application") {
            titleRes = R.string.settings_application_category
            summaryRes = R.string.settings_application_summary
            icon = iconRes(R.drawable.ic_twotone_palette_24)
            onClick {
                startSettings<ApplicationScreen>()
                true
            }
        }

        //editor
        pref("editor") {
            titleRes = R.string.settings_editor_category
            summaryRes = R.string.settings_editor_summary
            icon = iconRes(R.drawable.ic_twotone_keyboard_24)
            onClick {
                startSettings<EditorScreen>()
                true
            }
        }

        //build
        pref("build") {
            icon = iconRes(R.drawable.ic_twotone_build_24)
            summaryRes = R.string.settings_build_summary
            titleRes = R.string.settings_build_category
        }

        //plugin
        pref("plugin") {
            icon = iconRes(R.drawable.ic_twotone_memory_24)
            summaryRes = R.string.settings_plugin_summary
            titleRes = R.string.settings_plugin_category
        }

        //about
        pref("about") {
            titleRes = R.string.settings_about_category
            onClick {
                getBindFragment()
                    .requireActivity()
                    .startActivity<GeneralActivity> {
                        putExtra("type", getJavaClass<AboutFragment>().name)
                    }
                true
            }
        }
    }


}