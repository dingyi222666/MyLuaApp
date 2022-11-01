package com.dingyi.myluaapp.ide.ui.tabs

import android.graphics.drawable.Drawable
import com.dingyi.myluaapp.ide.ui.android.tabs.AndroidTabInfo
import com.dingyi.myluaapp.openapi.actions.ActionGroup
import com.dingyi.myluaapp.openapi.actions.PlaceProvider
import java.beans.PropertyChangeSupport
import java.util.Objects


class TabInfo(component: AndroidTabInfo) : PlaceProvider {


    companion object {

        const val ACTION_GROUP = "actionGroup"
        const val ICON = "icon"

        const val COMPONENT = "component"
        const val TEXT = "text"

        const val HIDDEN = "hidden"
        const val ENABLED = "enabled"
    }

    private var myComponent: AndroidTabInfo = component

    private var myGroup: ActionGroup = ActionGroup.EMPTY_GROUP

    private val myChangeSupport = PropertyChangeSupport(this)

    private var myIcon: Drawable? = null

    private var myPlace: String = ""
    private var myObject: Any = this


    private var myTabActionPlace = ""

    private var myHidden = false

    private val myText: String = ""


    private var myTooltipText: String = myText


    private var myEnabled = true


    fun getChangeSupport(): PropertyChangeSupport {
        return myChangeSupport
    }


    fun setText(text: String): TabInfo {

        clearText(true)
        myComponent.text = text
        return this
    }


    fun clearText(invalidate: Boolean): TabInfo {
        val old: String = myText
        if (invalidate) {
            myChangeSupport.firePropertyChange(TEXT, old, myText)
        }
        return this
    }


    fun setIcon(icon: Drawable): TabInfo {
        val old = myIcon
        if (old != icon) {
            myIcon = icon
            myChangeSupport.firePropertyChange(ICON, old, icon)
        }
        return this
    }


    fun setComponent(c: AndroidTabInfo): TabInfo {
        if (myComponent !== c) {
            val old = myComponent
            myComponent = c
            myChangeSupport.firePropertyChange(COMPONENT, old, myComponent)
        }
        return this
    }

    fun getGroup(): ActionGroup? {
        return myGroup
    }

    fun getComponent(): AndroidTabInfo {
        return myComponent
    }


    override fun getPlace(): String {
        return myPlace
    }

    fun setActions(group: ActionGroup, place: String): TabInfo {
        val old = myGroup
        myGroup = group
        myPlace = place
        myChangeSupport.firePropertyChange(ACTION_GROUP, old, myGroup)
        return this
    }


    fun setObject(`object`: Any): TabInfo {
        myObject = `object`
        return this
    }

    fun getObject(): Any {
        return myObject
    }


    fun getTabActionPlace(): String {
        return myTabActionPlace
    }


    override fun toString(): String {
        return myText
    }


    fun setHidden(hidden: Boolean) {
        val old = myHidden
        myHidden = hidden
        myChangeSupport.firePropertyChange(HIDDEN, old, myHidden)
    }

    fun isHidden(): Boolean {
        return myHidden
    }

    fun setEnabled(enabled: Boolean) {
        val old = myEnabled
        myEnabled = enabled
        myComponent.enabled = enabled
        myChangeSupport.firePropertyChange(ENABLED, old, myEnabled)
    }

    fun isEnabled(): Boolean {
        return myEnabled
    }


    fun setTooltipText(text: String): TabInfo {
        val old = myTooltipText
        if (!Objects.equals(old, text)) {
            myTooltipText = text
            myComponent.tooltipText = text
            myChangeSupport.firePropertyChange(TEXT, old, myTooltipText)
        }
        return this
    }


    fun getTooltipText(): String {
        return myTooltipText
    }


}