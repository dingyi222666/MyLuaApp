package com.dingyi.myluaapp.plugin.dsl.menu

import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu


fun Menu.dsl(dsl: MenuConfig.() -> Unit) {
    val config = MenuConfig()
    config.item = this
    dsl(config)
}




class MenuConfig {

    lateinit var item: Menu

    private fun applyMenu(menu: Menu, it: Pair<String, MenuItemConfig.() -> Unit>): MenuItem {


        val item = menu.add(it.first)

        item.dsl(it.second)

        return item

    }

    private fun applySubMenu(menu: Menu, it: Pair<String, MenuItemConfig.() -> Unit>): SubMenu {


        val item = menu.addSubMenu(it.first)

        item.item.dsl(it.second)

        return item

    }


    fun menu(name: String, dsl: MenuItemConfig.() -> Unit = {}): MenuItem {
        return applyMenu(item, name to  dsl)
    }

    fun submenu(name: String, dsl: MenuItemConfig.() -> Unit = {}): SubMenu {
        return applySubMenu(item, name to dsl)
    }
}
