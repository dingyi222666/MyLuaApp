package com.dingyi.myluaapp.ui.settings

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.ktx.*
import com.dingyi.myluaapp.databinding.FragmentSettingsBinding
import com.dingyi.myluaapp.ui.GeneralActivity
import com.hjq.language.MultiLanguages
import de.Maxr1998.modernpreferences.PreferenceScreen
import de.Maxr1998.modernpreferences.PreferencesAdapter
import de.Maxr1998.modernpreferences.helpers.*
import de.Maxr1998.modernpreferences.preferences.choice.SelectionItem
import java.util.*
import kotlin.concurrent.thread

class SettingsFragment : BaseFragment<FragmentSettingsBinding, MainViewModel>() {

    override fun getViewModelClass(): Class<MainViewModel> {
        return getJavaClass()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            val targetMethod = it.getString("method") ?: "main"

            val methodStringArg = it.getString("arg") ?: ""


            val methodInstance = if (methodStringArg.isEmpty()) {
                getJavaClass<SettingsFragment>()
                    .getMethod(targetMethod)
            } else {
                getJavaClass<SettingsFragment>()
                    .getMethod(targetMethod, getJavaClass<String>())
            }

            val screen = if (methodStringArg.isEmpty()) methodInstance.invoke(this) else
                methodInstance.invoke(this, methodStringArg)

            if (screen is PreferenceScreen) {
                val activity = requireActivity()
                if (activity is AppCompatActivity) {
                    activity.supportActionBar?.apply {
                        title = screen.title
                        setDisplayHomeAsUpEnabled(true)
                    }
                }

                val preferencesAdapter = PreferencesAdapter(screen)
                viewBinding.list.apply {

                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    adapter = preferencesAdapter
                }

                preferencesAdapter.setRootScreen(screen)
            }
        }

    }


    override fun getViewBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }


    private fun getLanguageSelectionItem(): List<SelectionItem> {
        val settingsApplicationLanguageEntry = requireContext()
            .getStringArray(R.array.settings_application_language_entry)

        val settingsApplicationLanguageValue = requireContext()
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
                "default" -> MultiLanguages.setSystemLanguage(requireContext())
                "chinese" -> MultiLanguages.setAppLanguage(requireContext(), Locale.CHINESE)
                "english" -> MultiLanguages.setAppLanguage(requireContext(), Locale.ENGLISH)
                else -> false
            }



        if (restart) {
            thread {
                requireActivity().runOnUiThread {
                    R.string.settings_editor_language_restart_toast
                        .getString()
                        .showToast()
                }
                Thread.sleep(500)
                android.os.Process.killProcess(android.os.Process.myPid())    //获取PID
            }
        }
    }

    fun application() = screen(requireActivity()) {
        title = R.string.settings_application_category.getString()


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


    /**
     * The main function will return preferences dsl of the settings fragment
     */
    fun main() = screen(requireActivity()) {
        title = R.string.settings_main_title.getString()

        //application
        pref("application") {
            titleRes = R.string.settings_application_category
            summaryRes = R.string.settings_application_summary
            icon = iconRes(R.drawable.ic_twotone_palette_24)
            onClick {
                startSettings("application")
                true
            }
        }

        //editor
        pref("editor") {
            titleRes = R.string.settings_editor_category
            summaryRes = R.string.settings_editor_summary
            icon = iconRes(R.drawable.ic_twotone_keyboard_24)
        }

        //build
        pref("build") {
            icon = iconRes(R.drawable.ic_twotone_build_24)
            summaryRes = R.string.settings_build_summary
            titleRes =R.string.settings_build_category
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
        }
    }


    private fun startSettings(method: String, arg: String? = null) {
        requireActivity().startActivity<GeneralActivity> {
            putExtra("type", getJavaClass<SettingsFragment>().name)
            putExtra("arg",
                Bundle().apply {
                    putString("method", method)
                    putString("arg", arg)
                }
            )
        }

    }


    private fun iconRes(
        iconRes: Int,
        tintRes: Int = R.attr.theme_hintTextColor
    ): Drawable? {
        val drawable = AppCompatResources
            .getDrawable(requireContext(), iconRes)

        if (tintRes != 0) {
            val color = requireContext().getAttributeColor(tintRes)
            drawable?.setTint(color)
        }
        return drawable
    }
}