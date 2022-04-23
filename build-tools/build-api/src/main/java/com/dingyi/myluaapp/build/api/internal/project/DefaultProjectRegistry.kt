package com.dingyi.myluaapp.build.api.internal.project

import com.dingyi.myluaapp.build.api.internal.ProjectInternal
import com.dingyi.myluaapp.build.api.sepcs.Spec
import com.dingyi.myluaapp.build.util.Path
import java.io.File

class DefaultProjectRegistry : ProjectRegistry<ProjectInternal> {
    private val projects: MutableMap<String, ProjectInternal> = HashMap()
    private val subProjects: MutableMap<String, MutableSet<ProjectInternal>> = HashMap()

    override fun addProject(project: ProjectInternal) {
        projects[project.getPath()] = project
        subProjects[project.getPath()] = mutableSetOf()
        addProjectToParentSubProjects(project)
    }

    fun removeProject(path: String): ProjectInternal? {
        val project = projects.remove(path)
        subProjects.remove(path)
        var loopProject = project?.getParentIdentifier()
        while (loopProject != null) {
            subProjects[loopProject.getPath()]?.remove(project)
            loopProject = loopProject.getParentIdentifier()
        }
        return project
    }

    private fun addProjectToParentSubProjects(project: ProjectInternal) {
        var loopProject = project.getParentIdentifier()
        while (loopProject != null) {
            subProjects[loopProject.getPath()]?.add(project)
            loopProject = loopProject.getParentIdentifier()
        }
    }

    override fun size(): Int {
        return projects.size
    }

    override fun getAllProjects(): Set<ProjectInternal> {
        return projects.values.toSet()
    }

    override fun getRootProject(): ProjectInternal {
        return checkNotNull(getProject(Path.ROOT.path))
    }

    override fun getProject(path: String): ProjectInternal? {
        return projects[path]
    }

    override fun getProject(projectDir: File): ProjectInternal? {
        val projects = findAll {
            it.getProjectDir() == projectDir
        }
        if (projects.size > 1) {
            throw RuntimeException(
                String.format(
                    "Found multiple projects with project directory '%s': %s",
                    projectDir, projects
                )
            )
        }
        return if (projects.size == 1) projects.iterator().next() else null
    }

    override fun getAllProjects(path: String): Set<ProjectInternal> {
        val result = getSubProjects(path).toMutableSet()
        val currentProject = projects[path]
        if (currentProject != null) {
            result.add(currentProject)
        }
        return result
    }

    override fun getSubProjects(path: String): Set<ProjectInternal> {
        return subProjects[path] ?: emptySet()
    }

    override fun findAll(constraint: Spec<in ProjectInternal>): Set<ProjectInternal> {
        val matches = mutableSetOf<ProjectInternal>()
        for (project in projects.values) {
            if (constraint.isSatisfiedBy(project)) {
                matches.add(project)
            }
        }
        return matches
    }
}