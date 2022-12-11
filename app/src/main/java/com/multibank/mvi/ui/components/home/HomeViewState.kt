package com.multibank.mvi.ui.components.home

import android.os.Build
import com.multibank.mvi.ui.base.mvi.MviViewState
import com.multibank.mvi.ui.base.mvi.ViewStateEmptyEvent
import com.multibank.mvi.ui.base.mvi.ViewStateErrorEvent
import com.multibank.mvi.ui.components.home.mvi.HomeResult
import com.multibank.mvi.ui.components.home.mvi.HomeResult.*
import com.multibank.mvi.ui.model.Task
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class HomeViewState(
    val inProgress: Boolean,
    val tasks: List<Task>?,
    val newTaskAdded: ViewStateEmptyEvent?,
    val error: ViewStateErrorEvent?
): MviViewState<HomeResult> {

    companion object {

        fun default() = HomeViewState(false, null, null, null)
    }

    override fun reduce(result: HomeResult): HomeViewState {
        return when (result) {
            is InProgressResult -> result.reduce()
            is ErrorResult -> result.reduce()
            is LoadTasksResult -> result.reduce()
            is AddTaskResult -> result.reduce()
            is UpdateTaskResult -> result.reduce()
            is DeleteCompletedTasksResult -> result.reduce()
            is DeleteTaskResult -> result.reduce()
            is LoadSingleTasksResult -> result.reduce()
        }
    }

    private fun InProgressResult.reduce() = this@HomeViewState.copy(
        inProgress = true
    )

    private fun ErrorResult.reduce() = this@HomeViewState.copy(
        inProgress = false,
        error = ViewStateErrorEvent(t)
    )

    private fun LoadTasksResult.reduce() = this@HomeViewState.copy(
        inProgress = false,
        tasks = tasks
    )

    private fun AddTaskResult.reduce() = this@HomeViewState.copy(
        inProgress = false,
        tasks = tasks?.toMutableList()?.apply { add(addedTask) }?.toList(),
        newTaskAdded = ViewStateEmptyEvent()
    )

    private fun LoadSingleTasksResult.reduce() = this@HomeViewState.copy(
        inProgress = false,
        tasks = tasks?.map { if (it.id == singleTask.id) singleTask else it }
    )

    private fun UpdateTaskResult.reduce() = this@HomeViewState.copy(
        inProgress = false,
        tasks = tasks?.map { if (it.id == updatedTask.id) updatedTask else it } // replace updated task in the tasks list
    )

    private fun DeleteTaskResult.reduce(): HomeViewState {

        val deletedIds = deletetask.id
        val newTasks = tasks?.toMutableList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newTasks?.removeIf { t->
                t.id == deletedIds
            }
        }
        return this@HomeViewState.copy(
            inProgress = false,
            tasks = newTasks
        )
    }

    private fun DeleteCompletedTasksResult.reduce(): HomeViewState {
        val deletedIds = deletedTasks.map { it.id }
        val newTasks = tasks?.toMutableList()
        newTasks?.removeAll { deletedIds.contains(it.id) }
        return this@HomeViewState.copy(
            inProgress = false,
            tasks = newTasks
        )
    }

    override fun isSavable() = !inProgress
}