package com.multibank.mvi.ui.components.home.mvi

import com.multibank.mvi.ui.base.mvi.MviResult
import com.multibank.mvi.ui.model.Task


sealed class HomeResult: MviResult {

    object InProgressResult: HomeResult()

    data class ErrorResult(val t: Throwable): HomeResult()

    data class LoadTasksResult(val tasks: List<Task>): HomeResult()

    data class LoadSingleTasksResult(val singleTask: Task): HomeResult()

    data class AddTaskResult(val addedTask: Task): HomeResult()

    data class UpdateTaskResult(val updatedTask: Task): HomeResult()

    data class DeleteTaskResult(val deletetask: Task): HomeResult()

    data class DeleteCompletedTasksResult(val deletedTasks: List<Task>): HomeResult()
}