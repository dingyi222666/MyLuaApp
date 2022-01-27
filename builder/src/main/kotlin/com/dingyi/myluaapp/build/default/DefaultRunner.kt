package com.dingyi.myluaapp.build.default

import android.provider.Settings
import android.util.Log
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.runner.Runner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DefaultRunner(
    private val project: Project
) : Runner {

    override fun run(type: String): Job {


        val builder = project.getBuilder()


        val tasks = when (type) {
            "clean" -> builder.clean()
            "build" -> builder.getTasks()
            "sync" -> builder.sync()
            else -> builder.getTasks()
        }


        Log.e("tasks","$tasks")

        val job = Job()
        val coroutineScope = CoroutineScope(Dispatchers.IO + job)

        coroutineScope.launch(Dispatchers.IO) {
            tasks.forEach {
                Log.e("task","$it")

                it.prepare()
                it.run()
            }
        }

        return job

    }
}