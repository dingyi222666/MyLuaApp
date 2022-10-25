package com.dingyi.myluaapp.openapi.components

import org.jetbrains.annotations.Nullable

interface PersistentStateComponent<T> {
    /**
     * @return a component state. All properties, public and annotated fields are serialized. Only values, which differ
     * from the default (i.e., the value of newly instantiated class) are serialized. `null` value indicates
     * that the returned state won't be stored, as a result previously stored state will be used.
     * @see com.intellij.util.xmlb.XmlSerializer
     */
    val state: T?

    /**
     * This method is called when new component state is loaded. The method can and will be called several times, if
     * config files were externally changed while IDE was running.
     *
     *
     * State object should be used directly, defensive copying is not required.
     *
     * @param state loaded component state
     * @see com.intellij.util.xmlb.XmlSerializerUtil.copyBean
     */
    fun loadState(state: T)

    /**
     * This method is called when the component is initialized, but no state is persisted.
     */
    fun noStateLoaded() {}

    /**
     * If class also is a service, then this method will be called after loading state (even if no state)
     * but only once throughout the life cycle.
     */
    fun initializeComponent() {}
}
