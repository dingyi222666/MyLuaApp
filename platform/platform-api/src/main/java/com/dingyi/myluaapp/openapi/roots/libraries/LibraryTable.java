package com.dingyi.myluaapp.openapi.roots.libraries;


import com.intellij.openapi.Disposable;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EventListener;
import java.util.Iterator;

/**
 * @see LibraryTablesRegistrar#getLibraryTable(com.intellij.openapi.project.Project)
 */

public interface LibraryTable {
    Library @NotNull [] getLibraries();

    @NotNull Library createLibrary();

    @NotNull Library createLibrary(@NonNls String name);

    void removeLibrary(@NotNull Library library);

    @NotNull
    Iterator<Library> getLibraryIterator();

    @Nullable
    Library getLibraryByName(@NotNull String name);

    @NotNull String getTableLevel();

    @NotNull LibraryTablePresentation getPresentation();

    default boolean isEditable() {
        return true;
    }

    /**
     * Returns the interface which allows to create or removed libraries from the table.
     * <strong>The returned model must be either committed {@link ModifiableModel#commit()} or disposed {@link com.intellij.openapi.util.Disposer#dispose(Disposable)}</strong>
     *
     * @return the modifiable library table model.
     */
    @NotNull
    ModifiableModel getModifiableModel();

    void addListener(@NotNull Listener listener);

    void addListener(@NotNull Listener listener, @NotNull Disposable parentDisposable);

    void removeListener(@NotNull Listener listener);

    interface ModifiableModel extends Disposable {
        @NotNull
        Library createLibrary(String name);

        void removeLibrary(@NotNull Library library);

        void commit();

        @NotNull
        Iterator<Library> getLibraryIterator();

        @Nullable
        Library getLibraryByName(@NotNull String name);

        Library @NotNull [] getLibraries();

        boolean isChanged();
    }

    interface Listener extends EventListener {

        default void afterLibraryAdded(@NotNull Library newLibrary) {
        }



        default void afterLibraryRenamed(@NotNull Library library, @Nullable String oldName) {
        }

        default void beforeLibraryRemoved(@NotNull Library library) {
        }

        default void afterLibraryRemoved(@NotNull Library library) {
        }
    }
}

