package com.dingyi.myluaapp.build.api.internal.project

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.file.collection.FileCollection
import com.dingyi.myluaapp.build.api.file.collection.FileTree
import com.dingyi.myluaapp.build.api.internal.BuildToolInternal
import com.dingyi.myluaapp.build.api.internal.ProjectInternal
import com.dingyi.myluaapp.build.api.plugins.ExtensionContainer
import com.dingyi.myluaapp.build.api.plugins.ObjectConfigurationAction
import com.dingyi.myluaapp.build.api.plugins.PluginContainer
import com.dingyi.myluaapp.build.api.plugins.PluginManager
import com.dingyi.myluaapp.build.api.services.get
import com.dingyi.myluaapp.build.api.tasks.TaskContainer
import com.dingyi.myluaapp.build.internal.services.ServiceRegistryFactory
import com.google.common.collect.Maps
import java.io.File

class DefaultProject(
    private val projectName: String,
    private val parentProject: ProjectInternal?,
    private val currentProjectDir: File,
    private val currentBuildFile: File,
    private val buildToolInternal: BuildToolInternal,
    private val servicesFactory: ServiceRegistryFactory,
):ProjectInternal {

    private var group: Any? = null
    private val childProjects = Maps.newTreeMap<String, ProjectInternal>()


    private val services = servicesFactory.createFor(this)

    private val taskContainer = services.get<TaskContainer>()

    private val projectRegistry = services.get<ProjectRegistry<ProjectInternal>>()

    private val rootProject = parentProject?.getRootProject() ?: this;

    override fun getBuildTool(): BuildToolInternal {
        return buildToolInternal
    }

    override fun addChildProject(childProject: ProjectInternal) {
        childProjects[childProject.getName()] = childProject
    }

    override fun project(path: String): ProjectInternal {
        TODO("Not yet implemented")
    }

    override fun project(referrer: ProjectInternal, path: String): ProjectInternal {
        TODO("Not yet implemented")
    }

    override fun project(
        referrer: ProjectInternal,
        path: String,
        configureAction: Action<in Project>
    ): ProjectInternal {
        TODO("Not yet implemented")
    }

    override fun project(path: String, configureAction: Action<in Project>): Project {
        TODO("Not yet implemented")
    }

    override fun findProject(path: String): ProjectInternal {
        TODO("Not yet implemented")
    }

    override fun findProject(referrer: ProjectInternal, path: String): ProjectInternal {
        TODO("Not yet implemented")
    }

    override fun getSubprojects(referrer: ProjectInternal): Set<ProjectInternal> {
        return projectRegistry.getSubProjects(getPath())
    }

    override fun getSubprojects(): Set<ProjectInternal> {
       return getSubprojects(this)
    }

    override fun subprojects(referrer: ProjectInternal, configureAction: Action<in Project>) {
        TODO("Not yet implemented")
    }

    override fun subprojects(action: Action<in Project>) {
        TODO("Not yet implemented")
    }

    override fun getAllprojects(referrer: ProjectInternal): Set<ProjectInternal> {
        TODO("Not yet implemented")
    }

    override fun getAllprojects(): Set<Project> {
        TODO("Not yet implemented")
    }

    override fun allprojects(referrer: ProjectInternal, configureAction: Action<in Project>) {
        TODO("Not yet implemented")
    }

    override fun allprojects(action: Action<in Project>) {
        TODO("Not yet implemented")
    }

    override fun getParent(): ProjectInternal? {
        return parentProject
    }

    override fun getAllProject(): Set<Project> {
        TODO("Not yet implemented")
    }

    override fun getPath(): String {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getChildProjects(): Map<String, Project> {
        return childProjects
    }


    override fun file(path: Any): File {
        TODO("Not yet implemented")
    }

    override fun fileTree(arg: Map<String, String>): FileTree {
        TODO("Not yet implemented")
    }

    override fun files(vararg path: Any): FileCollection {
        TODO("Not yet implemented")
    }

    override fun getBuildDir(): File {
        TODO("Not yet implemented")
    }

    override fun setBuildDir(path: File) {
        TODO("Not yet implemented")
    }

    override fun getGroup(): Any {
        group = group ?: if (this === rootProject) {
            ""
        } else rootProject.getName() + if (getParent() === rootProject) "" else "." + getParent()?.getPath()
                ?.substring(1)?.replace(':', '.')

        return checkNotNull(group)
    }

    override fun setGroup(group: Any) {
        this.group = group
    }

    override fun getStatus(): Any {
        TODO("Not yet implemented")
    }

    override fun setStatus(status: Any) {
        TODO("Not yet implemented")
    }

    override fun getProject(): Project {
        return this
    }

    override fun getTasks(): TaskContainer {
        TODO("Not yet implemented")
    }

    override fun beforeEvaluate(action: Action<in Project>) {
        TODO("Not yet implemented")
    }

    override fun afterEvaluate(action: Action<in Project>) {
        TODO("Not yet implemented")
    }

    override fun getDepth(): Int {
        TODO("Not yet implemented")
    }

    override fun absoluteProjectPath(path: String): String {
        TODO("Not yet implemented")
    }

    override fun relativeProjectPath(path: String): String {
        TODO("Not yet implemented")
    }

    override fun getRootProject(): Project {
        return rootProject
    }

    override fun getRootDir(): File {
        return rootProject.getRootDir()
    }

    override fun getProperty(key: String): Any {
        TODO("Not yet implemented")
    }

    override fun setProperty(key: String, value: Any) {
        TODO("Not yet implemented")
    }

    override fun findProperty(key: String): Any? {
        TODO("Not yet implemented")
    }

    override fun getPlugins(): PluginContainer {
        TODO("Not yet implemented")
    }

    override fun apply(action: Action<in ObjectConfigurationAction>) {
        TODO("Not yet implemented")
    }

    override fun apply(options: Map<String?, *>) {
        TODO("Not yet implemented")
    }

    override fun getPluginManager(): PluginManager {
        TODO("Not yet implemented")
    }

    override fun getExtensions(): ExtensionContainer {
        TODO("Not yet implemented")
    }

    override fun getParentIdentifier(): ProjectIdentifier? {
        TODO("Not yet implemented")
    }

    override fun getProjectDir(): File {
        TODO("Not yet implemented")
    }

    override fun getBuildFile(): File {
        TODO("Not yet implemented")
    }


}