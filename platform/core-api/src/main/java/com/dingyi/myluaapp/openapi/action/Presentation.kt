package com.dingyi.myluaapp.openapi.action

import android.graphics.drawable.Drawable
import org.jetbrains.annotations.NonNls
import org.jetbrains.kotlin.com.intellij.util.BitUtil
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.util.*
import java.util.function.Supplier


/**
 * Controls how an action would look in the UI
 */
class Presentation(text: Supplier<String?>) : Cloneable {

    private var myChangeSupport: PropertyChangeSupport? = null


    private var mIcon: Drawable? = null

    private var mTextSupplier: Supplier<String?> = text
    private var mDescriptionSupplier: Supplier<String?> =
        NULL_STRING

    private val IS_ENABLED = 0x1
    private val IS_VISIBLE = 0x2

    private val IS_MULTI_CHOICE = 0x4
    private val IS_POPUP_GROUP = 0x10
    private val IS_PERFORM_GROUP = 0x20
    private val IS_TEMPLATE = 0x1000

    private var myFlags = IS_ENABLED or IS_VISIBLE

    constructor() : this(NULL_STRING)

    constructor(text: String) : this({ text })


    fun addPropertyChangeListener(l: PropertyChangeListener) {
        var support = myChangeSupport
        if (myChangeSupport == null) {
            support = PropertyChangeSupport(this)
            myChangeSupport = support
        }
        support?.addPropertyChangeListener(l)
    }

    fun removePropertyChangeListener(l: PropertyChangeListener) {
        val support = myChangeSupport
        support?.removePropertyChangeListener(l)
    }


    /** @see Presentation.setVisible
     */
    fun isVisible(): Boolean {
        return BitUtil.isSet(myFlags, IS_VISIBLE)
    }

    /**
     * Sets whether the action is visible in menus, toolbars and popups or not.
     */
    fun setVisible(visible: Boolean) {
        val oldVisible = BitUtil.isSet(myFlags, IS_VISIBLE)
        myFlags = BitUtil.set(myFlags, IS_VISIBLE, visible)
        fireBooleanPropertyChange(PROP_VISIBLE, oldVisible, visible)
    }


    /**
     * For an action group presentation sets whether the action group is a popup group or not.
     * A popup action group is shown as a submenu, a toolbar button that shows a popup when clicked, etc.
     * A non-popup action group child actions are injected into the group parent group.
     */
    fun setPopupGroup(popup: Boolean) {
        myFlags = BitUtil.set(myFlags, IS_POPUP_GROUP, popup)
    }

    /** @see Presentation.setPerformGroup
     */
    fun isPerformGroup(): Boolean {
        return BitUtil.isSet(myFlags, IS_PERFORM_GROUP)
    }

    /**
     * For an action group presentation sets whether the action group is "performable" as an ordinary action or not.
     */
    fun setPerformGroup(performing: Boolean) {
        myFlags = BitUtil.set(myFlags, IS_PERFORM_GROUP, performing)
    }

    /**
     * Template presentations must be returned by [AnAction.getTemplatePresentation] only.
     * Template presentations assert that their enabled and visible flags are never updated
     * because menus and shortcut processing use different defaults,
     * so values from template presentations are silently ignored.
     */
    fun isTemplate(): Boolean {
        return BitUtil.isSet(myFlags, IS_TEMPLATE)
    }

    /** @see Presentation.setEnabled
     */
    fun isEnabled(): Boolean {
        return BitUtil.isSet(myFlags, IS_ENABLED)
    }

    /**
     * Sets whether the action is enabled or not. If an action is disabled, [AnAction.actionPerformed] is not be called.
     * In case when action represents a button or a menu item, the representing button or item will be greyed out.
     */
    fun setEnabled(enabled: Boolean) {
        val oldEnabled = BitUtil.isSet(myFlags, IS_ENABLED)
        myFlags = BitUtil.set(myFlags, IS_ENABLED, enabled)
        fireBooleanPropertyChange(PROP_ENABLED, oldEnabled, enabled)
    }

    fun setEnabledAndVisible(enabled: Boolean) {
        setEnabled(enabled)
        setVisible(enabled)
    }

    /** @see Presentation.setPopupGroup
     */
    fun isPopupGroup(): Boolean {
        return BitUtil.isSet(myFlags, IS_POPUP_GROUP)
    }


    fun getText(): String? {
        return mTextSupplier.get()
    }


    override fun clone(): Presentation {
       return kotlin.runCatching {
            super.clone() as Presentation
        }.getOrThrow()
    }

    fun setIcon(icon: Drawable?) {
        val old = mIcon
        fireObjectPropertyChange("icon", old, icon)
        mIcon = icon
    }

    fun getIcon(): Drawable? {
        return mIcon
    }

    fun setText(text: String?) {
        setText { text }
    }

    fun setText(text: Supplier<String?>) {
        mTextSupplier = text
    }

    fun setDescription(description: Supplier<String?>) {
        val oldDescription = mDescriptionSupplier
        mDescriptionSupplier = description
        fireObjectPropertyChange(PROP_DESCRIPTION, oldDescription, mDescriptionSupplier)
    }

    private fun fireObjectPropertyChange(propertyName: String, oldValue: Any?, newValue: Any?) {
        val support = myChangeSupport;
        if (Objects.equals(oldValue, newValue)) {
            support?.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    private fun fireBooleanPropertyChange(
        propertyName: String,
        oldValue: Boolean,
        newValue: Boolean
    ) {
        val support = myChangeSupport;
        if (Objects.equals(oldValue, newValue)) {
            support?.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    companion object {

        /**
         * Defines tool tip for button at toolbar or text for element at menu
         * value: String
         */
        @NonNls
        const val PROP_TEXT = "text"

        /**
         * value: String
         */
        @NonNls
        const val PROP_DESCRIPTION = "description"

        /**
         * value: Icon
         */
        @NonNls
        const val PROP_ICON = "icon"


        /**
         * value: Boolean
         */
        @NonNls
        const val PROP_VISIBLE = "visible"

        /**
         * The actual value is a Boolean.
         */
        @NonNls

        const val PROP_ENABLED = "enabled"

        @JvmStatic
        fun newTemplatePresentation(): Presentation {
            return Presentation()
        }

        val NULL_STRING: Supplier<String?> = Supplier<String?> { null }

    }

}