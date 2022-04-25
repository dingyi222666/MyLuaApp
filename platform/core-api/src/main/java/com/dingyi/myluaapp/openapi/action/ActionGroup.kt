package com.dingyi.myluaapp.openapi.action

import android.graphics.drawable.Drawable
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.util.function.Supplier


abstract class ActionGroup: AnAction {

    constructor(): super()

    constructor(icon: Drawable?) : super(Presentation.NULL_STRING, Presentation.NULL_STRING, icon)
    constructor(dynamicText: Supplier<String?>) : super(
        dynamicText,
        Presentation.NULL_STRING,
        null
    )

    @JvmOverloads
    constructor(
        text: String?,
        description: String? = null,
        icon: Drawable? = null
    ) : super({ text }, { description }, icon)

    constructor(dynamicText: Supplier<String?>, icon: Drawable?) : super(
        dynamicText,
        Presentation.NULL_STRING,
        icon
    )

    constructor(
        dynamicText: Supplier<String?>,
        description: Supplier<String?>,
        icon: Drawable?
    ) : super(dynamicText, description, icon)

    private var mySearchable = true
    private val myChangeSupport = PropertyChangeSupport(this)

    companion object {

        private val EMPTY_ARRAY = arrayOf<AnAction>()

        val EMPTY_GROUP = object : ActionGroup() {
            override fun getChildren(e: AnActionEvent):  Array<AnAction> {
                return EMPTY_ARRAY
            }
        }
    }

    private var mySecondaryActions =  mutableSetOf<AnAction>()

    /**
     * The actual value is a Boolean.
     */

    private val PROP_POPUP = "popup"

    private var myDumbAware: Boolean? = null


    /**
     * This method can be called in popup menus if [.canBePerformed] is `true`.
     */
    override fun actionPerformed(e: AnActionEvent) {}



    /**
     * Returns the default value of the popup flag for the group.
     * @see Presentation.setPopupGroup
     */
    open fun isPopup(): Boolean {
        return templatePresentation.isPopupGroup()
    }


    /**
     * Sets the default value of the popup flag for the group.
     * A popup group is shown as a popup in menus.
     *
     * In the [AnAction.update] method `event.getPresentation().setPopupGroup(value)`
     * shall be used instead of this method to control the popup flag for the particular event and place.
     *
     * If the [.isPopup] method is overridden, this method could be useless.
     *
     * @param popup If `true` the group will be shown as a popup in menus.
     * @see Presentation.setPopupGroup
     */
    fun setPopup(popup: Boolean) {
        val presentation = templatePresentation
        val oldPopup = presentation.isPopupGroup()
        presentation.setPopupGroup(popup)
        firePropertyChange(PROP_POPUP, oldPopup, popup)
    }

    open fun isSearchable(): Boolean {
        return mySearchable
    }

    open fun setSearchable(searchable: Boolean) {
        mySearchable = searchable
    }

    fun addPropertyChangeListener(l: PropertyChangeListener) {
        myChangeSupport.addPropertyChangeListener(l)
    }

    fun removePropertyChangeListener(l: PropertyChangeListener) {
        myChangeSupport.removePropertyChangeListener(l)
    }

    protected fun firePropertyChange(propertyName: String, oldValue: Any?, newValue: Any?) {
        myChangeSupport.firePropertyChange(propertyName, oldValue, newValue)
    }

    /**
     * Returns the children of the group.
     *
     * @return An array representing children of this group. All returned children must be not `null`.
     */
    abstract fun getChildren(e: AnActionEvent): Array<AnAction>

    open fun getChildren(
         e: AnActionEvent,
         actionManager: ActionManager
    ):  Array<AnAction> {
        return getChildren(e)
    }

    fun setAsPrimary(action: AnAction, isPrimary: Boolean) {
        if (isPrimary) {
            mySecondaryActions.remove(action)
        } else {
            mySecondaryActions.add(action)
        }
    }



    fun isPrimary(action: AnAction): Boolean {
        return !mySecondaryActions.contains(action)
    }

    protected fun replace(originalAction: AnAction, newAction: AnAction) {
        mySecondaryActions.apply {
            if (contains(originalAction)) {
                remove(originalAction)
                add(newAction)
            }
        }
    }
}



