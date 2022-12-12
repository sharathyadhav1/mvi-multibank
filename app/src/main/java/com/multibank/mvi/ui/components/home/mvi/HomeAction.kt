package com.multibank.mvi.ui.components.home.mvi

import com.multibank.mvi.ui.base.mvi.MviAction


sealed class HomeAction: MviAction {

    object LoadTasksAction: HomeAction()

    data class AddTaskAction(val taskContent: String?): HomeAction()

    data class LoadSingleTasksAction(val taskId: Long): HomeAction()

    data class UpdateTaskAction(val taskId: Long, val isDone: Boolean): HomeAction()

    data class UpdateStatusAction(val taskId: Long, val status: String): HomeAction()

    data class DeleteStatusAction(val taskId: Long, val status: String): HomeAction()

    object DeleteCompletedTasksAction: HomeAction()

}