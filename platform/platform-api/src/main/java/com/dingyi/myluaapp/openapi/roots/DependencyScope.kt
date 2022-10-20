// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.roots

import org.jetbrains.annotations.NonNls
import java.util.function.Supplier

/**
 * The table below specifies which order entries are used during compilation and runtime.
 * <table border=1>
 * <thead><td></td><td>Production<br></br>Compile</td><td>Production<br></br>Runtime</td>
 * <td>Test<br></br>Compile</td><td>Test<br></br>Runtime</td></thead>
 * <tbody>
 * <tr><td>[.COMPILE]</td>      <td>*</td><td>*</td><td>*</td><td>*</td></tr>
 * <tr><td>[.TEST]</td>         <td> </td><td> </td><td>*</td><td>*</td></tr>
 * <tr><td>[.RUNTIME]</td>      <td> </td><td>*</td><td> </td><td>*</td></tr>
 * <tr><td>[.PROVIDED]</td>     <td>*</td><td> </td><td>*</td><td>*</td></tr>
 * <tr><td>Production<br></br>Output</td> <td> </td><td>*</td><td>*</td><td>*</td></tr>
 * <tr><td>Test<br></br>Output</td>       <td> </td><td> </td><td> </td><td>*</td></tr>
</tbody> *
</table> *
 * <br></br>
 *
 * Note that the way dependencies are processed may be changed by plugins if the project is imported from a build system. So values from
 * this enum are supposed to be used only to edit dependencies (via [ExportableOrderEntry.setScope]). If you need to determine which
 * dependencies are included into a classpath, use [OrderEnumerator].
 */
enum class DependencyScope(
    private val myDisplayName: Supplier<String>,
    val isForProductionCompile: Boolean,
    val isForProductionRuntime: Boolean,
    val isForTestCompile: Boolean,
    val isForTestRuntime: Boolean
) {
    COMPILE(Supplier { "compile" }, true, true, true, true), TEST(
        Supplier { "test" }, false, false, true, true
    ),
    RUNTIME(
        Supplier { "runtime" }, false, true, false, true
    ),
    PROVIDED(
        Supplier { "provided" }, true, false, true, true
    );

    /* @NotNull
  public static DependencyScope readExternal(@NotNull Element element) {
    String scope = element.getAttributeValue(SCOPE_ATTR);
    if (scope != null) {
      try {
        return valueOf(scope);
      }
      catch (IllegalArgumentException e) {
        return COMPILE;
      }
    }
    else {
      return COMPILE;
    }
  }

  public void writeExternal(Element element) {
    if (this != COMPILE) {
      element.setAttribute(SCOPE_ATTR, name());
    }
  }*/
    val displayName: String
        get() = myDisplayName.get()

    override fun toString(): String {
        return displayName
    }

    companion object {
        @NonNls
        val SCOPE_ATTR = "scope"
    }
}