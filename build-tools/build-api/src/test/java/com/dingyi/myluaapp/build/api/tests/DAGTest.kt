package com.dingyi.myluaapp.build.api.tests

import com.google.common.util.concurrent.ThreadFactoryBuilder
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.DirectedAcyclicGraph
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

class DAGTest {

    @Test
    fun test1() {

        val taskContainer = TaskContainer()

        val task1 = Task("task1") {
            Thread.sleep(1000)
            println("task1")
        }
        val task2 = Task("task2") {
            Thread.sleep(500)
            println("task2")
        }
        val task3 = Task("task3") {
            Thread.sleep(1000)
            println("task3")
        }

        val task4 = Task("task4") {
            Thread.sleep(1000)
            println("task4")
        }

        task3.dependsOn(task1)
        task4.dependsOn(task3)
        task4.dependsOn(task2)

        taskContainer.addTask(task4)
        val scheduler = TaskScheduler(taskContainer)

        scheduler.schedule()
    }


}


class TaskScheduler(private val taskContainer: TaskContainer) {

    private val graph = DirectedAcyclicGraph<Task, DefaultEdge>(DefaultEdge::class.java)

    private var isPrepareGraph = false


    private val graphLock = ReentrantLock()

    private val threadFactory = ThreadFactoryBuilder().setNameFormat("task-scheduler-%d").build()

    private val executor =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2, threadFactory)


    private fun buildGraph() {

        if (isPrepareGraph) {
            return
        }

        taskContainer.getTasks().forEach { task ->
            graph.addVertex(task)
        }

        taskContainer.getTasks().forEach { task ->
            task.getDependencies()
                .mapNotNull {
                    taskContainer.resolveTask(it)
                }
                .forEach { dependency ->
                    graph.addEdge(task, dependency)
                }
        }
        isPrepareGraph = true
    }


    private fun runTaskList(tasks: List<Task>): CountDownLatch {
        val countDownLatch = CountDownLatch(tasks.size)

        tasks.forEach {
            executor.submit {
                it.run()
                graphLock.lock()
                graph.getAncestors(it).forEach { targetTask ->
                    graph.removeEdge(it, targetTask)
                }
                graph.removeVertex(it)
                graphLock.unlock()
                countDownLatch.countDown()
            }
        }
        return countDownLatch
    }

    fun schedule() {

        buildGraph()



        while (graph.vertexSet().isNotEmpty()) {

            val currentRunTaskList = mutableListOf<Task>()
            graphLock.lock()
            graph.vertexSet().forEach {

                val dependencyList = graph.getDescendants(it)
                if (dependencyList.isEmpty()) {
                    currentRunTaskList.add(it)
                }
            }
            graphLock.unlock()
            //wait for current task finish
            runTaskList(currentRunTaskList)
                .await()
        }


        executor.shutdown()
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS)

    }


}


class Task(val name: String, private val runnable: Runnable) {
    private val dependencies = mutableSetOf<Any>()

    fun dependsOn(task: Any) {
        dependencies.add(task)
    }

    fun getDependencies(): Set<Any> {
        return dependencies
    }

    fun run() {
        runnable.run()
    }

}


class TaskContainer() {

    private val tasks = mutableListOf<Task>()

    fun addTask(task: Task) {
        tasks.add(task)
        resolveTasks(*task.getDependencies().toTypedArray())
            .forEach {
                if (!hasTask(it)) {
                    addTask(it)
                }
            }
    }

    fun getTasks(): List<Task> {
        return tasks
    }

    fun hasTask(task: Task): Boolean {
        return tasks.contains(task)
    }

    fun getTask(name: String): Task? {
        return tasks.find { it.name == name }
    }

    fun resolveTask(task: Any): Task? {
        return when (task) {
            is String -> getTask(task)
            is Task -> task
            else -> null
        }
    }

    fun resolveTasks(vararg tasks: Any): List<Task> {
        return tasks.mapNotNull { resolveTask(it) }
    }


    fun removeTask(task: Task) {
        tasks.remove(task)
    }

    fun removeTask(name: String) {
        tasks.removeIf { it.name == name }
    }


}