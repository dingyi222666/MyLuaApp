/*
 * Copyright 2007-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dingyi.myluaapp.build.api.internal.project;


import com.dingyi.myluaapp.build.api.sepcs.Spec
import java.io.File;

interface ProjectRegistry<T : ProjectIdentifier> {
    fun addProject(project: T);

    fun getRootProject(): T?

    fun getProject(path: String): T?

    fun getProject(projectDir: File): T?

    fun size(): Int

    fun getAllProjects(): Set<T>

    fun getAllProjects(path: String): Set<T>

    fun getSubProjects(path: String): Set<T>

    fun findAll(constraint: Spec<in T>): Set<T>
}
