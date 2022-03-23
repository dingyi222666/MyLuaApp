package com.dingyi.myluaapp.ui.settings

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.ktx.getAttributeColor
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.startActivity
import com.dingyi.myluaapp.ui.GeneralActivity
import de.Maxr1998.modernpreferences.PreferenceScreen.Builder

interface SettingScreen {

    fun create(screen: Builder): Builder

    fun getBindFragment(): Fragment


    fun iconRes(
        iconRes: Int,
        tintRes: Int = R.attr.theme_hintTextColor
    ): Drawable? {
        val drawable = AppCompatResources
            .getDrawable(getBindFragment().requireContext(), iconRes)

        if (tintRes != 0) {
            val color = getBindFragment().requireContext().getAttributeColor(tintRes)
            drawable?.setTint(color)
        }
        return drawable
    }

    fun startSettings(clazz: Class<*>) {
        getBindFragment().requireActivity().startActivity<GeneralActivity> {
            putExtra("type", getJavaClass<SettingsFragment>().name)
            putExtra("arg",
                Bundle().apply {
                    putString("method", "callScreen")
                    putString("arg", clazz.name)
                }
            )
        }

    }


}

inline fun <reified T:Any> SettingScreen.startSettings() = startSettings(getJavaClass<T>())