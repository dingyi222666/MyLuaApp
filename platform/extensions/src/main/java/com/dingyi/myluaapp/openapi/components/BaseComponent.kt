package com.dingyi.myluaapp.openapi.components


@Deprecated(
    "Components are deprecated, please see <a href=\"https ://plugins.jetbrains.com/docs/intellij/plugin-components.html\">SDK Docs</a> for guidelines on migrating to other APIs."
)
interface BaseComponent : NamedComponent {

    @Deprecated("Use {@link com.intellij.openapi.components.PersistentStateComponent#initializeComponent()} or perform initialization in constructor for non-persistence component.")
    fun initComponent() {
    }

    @Deprecated("Use {@link com.intellij.openapi.Disposable}")
    fun disposeComponent() {
    }
}
