package com.dingyi.myluaapp.ui.settings

import android.os.Process
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.ktx.getString
import com.dingyi.myluaapp.common.ktx.getStringArray
import com.dingyi.myluaapp.common.ktx.showToast
import com.hjq.language.MultiLanguages
import de.Maxr1998.modernpreferences.PreferenceScreen
import de.Maxr1998.modernpreferences.helpers.onSelectionChange
import de.Maxr1998.modernpreferences.helpers.singleChoice
import de.Maxr1998.modernpreferences.preferences.choice.SelectionItem
import java.util.*
import kotlin.concurrent.thread

class ApplicationScreen(settingScreen: SettingScreen):SettingScreen by settingScreen {

    override fun create(screen: PreferenceScreen.Builder) =  screen.apply {
        titleRes = R.string.settings_application_category


        singleChoice(
            key = "language",
            items = getLanguageSelectionItem()
        ) {
            titleRes = R.string.settings_application_language_title
            summaryRes = R.string.settings_application_language_summary
            icon = iconRes(R.drawable.ic_twotone_translate_24)

            onSelectionChange {
                checkLanguage(it)
                true
            }

        }
    }


    private fun getLanguageSelectionItem(): List<SelectionItem> {
        val settingsApplicationLanguageEntry = getBindFragment().requireContext()
            .getStringArray(R.array.settings_application_language_entry)

        val settingsApplicationLanguageValue = getBindFragment().requireContext()
            .getStringArray(R.array.settings_application_language_entry_value)

        return settingsApplicationLanguageEntry.mapIndexed { index, s ->
            SelectionItem(
                key = settingsApplicationLanguageValue[index],
                title = s,
                summary = null
            )
        }

    }

    private fun checkLanguage(language: String) {
        val restart =
            when (language) {
                "default" -> MultiLanguages.setSystemLanguage(getBindFragment().requireContext())
                "chinese" -> MultiLanguages.setAppLanguage(getBindFragment().requireContext(), Locale.CHINESE)
                "english" -> MultiLanguages.setAppLanguage(getBindFragment().requireContext(), Locale.ENGLISH)
                else -> false
            }



        if (restart) {
            thread {
                getBindFragment().requireActivity().runOnUiThread {
                    R.string.settings_editor_language_restart_toast
                        .getString()
                        .showToast()
                }
                Thread.sleep(500)
                Process.killProcess(Process.myPid())    //获取PID
            }
        }
    }

}