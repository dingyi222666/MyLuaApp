package com.dingyi.myluaapp.plugin.dsl.menu

import android.view.MenuItem
import com.dingyi.myluaapp.common.ktx.checkNotNull

fun MenuItem.dsl(dsl: MenuItemConfig.() -> Unit) {
    val config = MenuItemConfig()
    config.item = this
    dsl(config)
}

fun MenuItem.click(click:(MenuItem)->Unit) {
    this.setOnMenuItemClickListener {
        click(it)
        true
    }
}

class MenuItemConfig {

    lateinit var item: MenuItem

    fun apply(menuItem: MenuItem, it: Triple<String, String, MenuItemConfig.() -> Unit>): MenuItem {

        val item = if (it.second == "submenu") {
            menuItem.subMenu?.addSubMenu(it.first)?.item
        } else {
            menuItem.subMenu?.add(it.first)
        }


        item?.dsl(it.third)

        return item.checkNotNull()

    }


    fun menu(name: String, dsl: MenuItemConfig.() -> Unit = {}): MenuItem {
        return apply(item, Triple(name, "menu", dsl))
    }

    fun submenu(name: String, dsl: MenuItemConfig.() -> Unit = {}): MenuItem {
        return apply(item, Triple(name, "submenu", dsl))
    }
}