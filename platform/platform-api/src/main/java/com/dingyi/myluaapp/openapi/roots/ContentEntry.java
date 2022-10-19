/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dingyi.myluaapp.openapi.roots;

import com.dingyi.myluaapp.openapi.vfs.VirtualFile;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a module's content root.
 * You can get existing entries with {@link ModuleRootModel#getContentEntries()} or
 * create a new one with {@link ModifiableRootModel#addContentEntry(VirtualFile)}. Note that methods which change the state can be called
 * only on instances of {@link ContentEntry} obtained from {@link ModifiableRootModel}. Calling these methods on instances obtained from
 * {@code ModuleRootManager.getInstance(module).getContentEntries()} may lead to failed assertion at runtime.
 *
 * @author dsl
 * @see ModuleRootModel#getContentEntries()
 * @see ModifiableRootModel#addContentEntry(VirtualFile)
 */

public interface ContentEntry extends Synthetic {
  /**
   * Returns the root file or directory for the content root, if it is valid.
   *
   * @return the content root file or directory, or null if content entry is invalid.
   */
  @Nullable
  VirtualFile getFile();

  /**
   * Returns the URL of content root.
   * To validate returned roots, use
   * <code>{@link com.intellij.openapi.vfs.VirtualFileManager#findFileByUrl(String)}</code>
   *
   * @return URL of content root, that should never be null.
   */
  @NotNull
  String getUrl();

  /**
   * Returns the list of source roots under this content root.
   *
   * @return array of this {@code ContentEntry} {@link SourceFolder}s
   */
  SourceFolder @NotNull [] getSourceFolders();


  /**
   * Returns the list of files and directories for valid source roots under this content root.
   *
   * @return array of all valid source roots.
   */
  VirtualFile @NotNull [] getSourceFolderFiles();

  /**
   * Returns the list of excluded roots configured under this content root. The result doesn't include synthetic excludes like the module output.
   *
   * @return array of this {@code ContentEntry} {@link ExcludeFolder}s
   */
  ExcludeFolder @NotNull [] getExcludeFolders();

  /**
   * @return list of URLs for all excluded roots under this content root including synthetic excludes like the module output
   */
  @NotNull
  List<String> getExcludeFolderUrls();

  /**
   * Returns the list of files and directories for valid excluded roots under this content root.
   *
   * @return array of all valid exclude roots including synthetic excludes like the module output
   */
  VirtualFile @NotNull [] getExcludeFolderFiles();

  /**
   * Adds a source or test source root under the content root. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   *
   * @param file         the file or directory to add as a source root.
   * @param isTestSource true if the file or directory is added as a test source root.
   * @return the object representing the added root.
   */
  @NotNull
  SourceFolder addSourceFolder(@NotNull VirtualFile file, boolean isTestSource);

  /**
   * Adds a source or test source root with the specified package prefix under the content root. This method may be called only on an
   * instance obtained from {@link ModifiableRootModel}.
   *
   * @param file          the file or directory to add as a source root.
   * @param isTestSource  true if the file or directory is added as a test source root.
   * @param packagePrefix the package prefix for the root to add, or an empty string if no
   *                      package prefix is required.
   * @return the object representing the added root.
   */
  @NotNull
  SourceFolder addSourceFolder(@NotNull VirtualFile file, boolean isTestSource, @NotNull String packagePrefix);


  /**
   * Adds a source or test source root under the content root. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   *
   * @param url          the file or directory url to add as a source root.
   * @param isTestSource true if the file or directory is added as a test source root.
   * @return the object representing the added root.
   */
  @NotNull
  SourceFolder addSourceFolder(@NotNull String url, boolean isTestSource);

  /**
   * Removes a source or test source root from this content root. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   *
   * @param sourceFolder the source root to remove (must belong to this content root).
   */
  void removeSourceFolder(@NotNull SourceFolder sourceFolder);

  /**
   * Removes all source roots. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   */
  void clearSourceFolders();

  /**
   * Adds an exclude root under the content root. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   *
   * @param file the file or directory to add as an exclude root.
   * @return the object representing the added root.
   */
  @NotNull
  ExcludeFolder addExcludeFolder(@NotNull VirtualFile file);

  /**
   * Adds an exclude root under the content root. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   *
   * @param url the file or directory url to add as an exclude root.
   * @return the object representing the added root.
   */
  @NotNull
  ExcludeFolder addExcludeFolder(@NotNull String url);
  ExcludeFolder addExcludeFolder(@NotNull String url, ProjectModelExternalSource source);

  /**
   * Removes an exclude root from this content root. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   *
   * @param excludeFolder the exclude root to remove (must belong to this content root).
   */
  void removeExcludeFolder(@NotNull ExcludeFolder excludeFolder);

  /**
   * Removes an exclude root from this content root. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   *
   * @param url url of the exclude root
   * @return {@code true} if the exclude root was removed
   */
  boolean removeExcludeFolder(@NotNull String url);

  /**
   * Removes all excluded folders. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   */
  void clearExcludeFolders();

  /**
   * Returns patterns for names of files which should be excluded from this content root. If name of a file under this content root matches
   * any of the patterns it'll be excluded from the module, if name of a directory matches any of the patterns the directory and all of its
   * contents will be excluded. '?' and '*' wildcards are supported.
   */
  @NotNull
  List<String> getExcludePatterns();

  /**
   * Adds a pattern for names of files which should be excluded. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   */
  void addExcludePattern(@NotNull String pattern);

  /**
   * Removes a pattern for names of files which should be excluded. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   */
  void removeExcludePattern(@NotNull String pattern);

  /**
   * Sets patterns for names of files which should be excluded. This method may be called only on an instance obtained from {@link ModifiableRootModel}.
   */
  void setExcludePatterns(@NotNull List<String> patterns);

  @NotNull
  ModuleRootModel getRootModel();
}
