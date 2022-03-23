package com.dingyi.myluaapp.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BaseFragment
import com.dingyi.myluaapp.common.ktx.*
import com.dingyi.myluaapp.databinding.FragmentSettingsBinding
import com.dingyi.myluaapp.ui.GeneralActivityViewModel
import de.Maxr1998.modernpreferences.PreferenceScreen
import de.Maxr1998.modernpreferences.PreferencesAdapter

class SettingsFragment : BaseFragment<FragmentSettingsBinding, GeneralActivityViewModel>() {


    override fun getViewModelClass(): Class<GeneralActivityViewModel> {
        return getJavaClass()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            val targetMethod = it.getString("method") ?: "callScreen"

            val methodStringArg = it.getString("arg") ?: getJavaClass<MainScreen>().name


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
                        title = screen.titleRes.getString()
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




    /**
     * The main function will return preferences dsl of the settings fragment
     */
    fun callScreen(
        clazz: String
    ): PreferenceScreen = requireActivity().classLoader.loadClass(clazz)
        .getConstructor(getJavaClass<com.dingyi.myluaapp.ui.settings.SettingScreen>())
        .newInstance(defaultScreen)
        .convertObject<com.dingyi.myluaapp.ui.settings.SettingScreen>()
        .create(PreferenceScreen.Builder(requireContext()))
        .build()



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_FONT_SET_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let {
                        requireContext().contentResolver.openInputStream(it)?.use { input ->
                            runCatching {
                                (Paths.fontsDir + "/default.ttf").toFile().run {
                                    if (!exists()) {
                                        parentFile?.mkdirs()
                                        createNewFile()
                                    }
                                    outputStream()
                                }.use { output ->
                                    input.copyTo(output)
                                }
                            }.isSuccess
                        }
                    }?.let {
                        if (it) R.string.settings_editor_font_toast_successful
                        else R.string.settings_editor_font_toast_fail
                    }?.getString()?.showToast()
                }
            }
        }


    }

    open inner class SettingScreen : com.dingyi.myluaapp.ui.settings.SettingScreen {
        override fun create(screen: PreferenceScreen.Builder): PreferenceScreen.Builder {
            return screen
        }

        override fun getBindFragment(): Fragment {
            return this@SettingsFragment
        }

    }



    private val defaultScreen = SettingScreen()

    companion object {
        const val REQUEST_FONT_SET_CODE = 1
    }
}