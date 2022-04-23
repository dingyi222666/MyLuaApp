package com.dingyi.myluaapp.openapi.action

import android.graphics.drawable.Drawable
import org.jetbrains.annotations.NonNls
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
    private val mIsVisible = false
    private val mIsEnabled = true

    private var mTextSupplier: Supplier<String?> = text
    private var mDescriptionSupplier: Supplier<String?> =
        NULL_STRING

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