package com.dingyi.myluaapp.openapi.editor.event

import java.util.EventListener

/**
 * Allows receiving notifications when editors are created and released.
 * To subscribe, use the `com.intellij.editorFactoryListener` extension point or
 * [com.dingyi.myluaapp.openapi.editor.EditorFactory.addEditorFactoryListener].
 */
interface EditorFactoryListener : EventListener {
    /**
     * Called after [com.dingyi.myluaapp.openapi.editor.Editor] instance has been created.
     */
    fun editorCreated(event: EditorFactoryEvent) {}

    /**
     * Called before [com.dingyi.myluaapp.openapi.editor.Editor] instance will be released.
     */
    fun editorReleased(event: EditorFactoryEvent) {}
}
