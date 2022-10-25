package com.dingyi.myluaapp.openapi.components


/**
 * @see [Persisting States](http://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html)
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class State(
    /**
     * Component name.
     */
    val name: String,
    /**
     *
     * Storages specification.
     *
     *
     * Project-level: optional, standard project file will be used by default
     * (`*.ipr` file for file-based and
     * `.idea/misc.xml` for directory-based).
     *
     *
     * Module-level: optional, corresponding module file will be used (`*.iml`).
     */
    val storages:Array<Storage> = [],


    // ---------------------------------------------------------------

    /**
     * If set to false, complete project (or application) reload is required when the storage file is changed externally and the state has changed.
     */
    /*val reloadable: Boolean = true,*/


    // ---------------------------------------------------------------

    /**
     * If true, default state will be loaded from resources (if exists).
     */
    val defaultStateAsResource: Boolean = false,

    // ---------------------------------------------------------------

    /**
     * Additional export directory path (relative to application-level configuration root directory).
     */
    /*@NotNull val additionalExportDirectory: String = "",
    @ApiStatus.ScheduledForRemoval(inVersion = "2021.3") @Deprecated("Use {@link #additionalExportDirectory()}.") val additionalExportFile: String = "",
    val presentableName: KClass<out NameGetter?> = NameGetter::class,*/


    // ---------------------------------------------------------------

    /**
     * Is this component intended to store data only in the external storage.
     */
    val externalStorageOnly: Boolean = false,
)

