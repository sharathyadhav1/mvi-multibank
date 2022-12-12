package com.multibank.mvi.ui.components.home.mvi

import com.multibank.mvi.data.repositories.TaskRepository
import com.multibank.mvi.ui.base.mvi.*
import com.multibank.mvi.ui.components.home.mvi.HomeAction.*
import com.multibank.mvi.ui.components.home.mvi.HomeResult.*
import com.multibank.mvi.ui.components.home.recyclerview.TasksAdapter
import com.multibank.mvi.ui.model.Task
import io.reactivex.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
/*import org.koin.core.component.KoinComponent
import org.koin.core.component.inject*/

import java.util.*


class HomeActionProcessor: MviActionsProcessor<HomeAction, HomeResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val taskRepository: TaskRepository by inject()

    override fun getActionProcessors(shared: Observable<HomeAction>) = listOf(
        shared.connect(loadTasksActionProcessor),
        shared.connect(loadTasksActionProcessor),
        shared.connect(addTaskActionProcessor),
        shared.connect(updateTaskActionProcessor),
        shared.connect(updateStatusActionProcessor),
        shared.connect(DeleteStatusActionProcessor),
        shared.connect(deletedCompletedTasksActionProcessor)
    )

    private val loadTasksActionProcessor = createActionProcessor<LoadTasksAction, HomeResult>(
        schedulersProvider,
        { InProgressResult },
        { t -> ErrorResult(t) }
    ) {
        onNextSafe(LoadTasksResult(taskRepository.getAllTasks()))
        onCompleteSafe()
    }

    private val addTaskActionProcessor = createActionProcessor<AddTaskAction, HomeResult>(
        schedulersProvider,
        { InProgressResult },
        { t -> ErrorResult(t) }
    ) { action ->
        val newTask = taskRepository.addTask(Task(0, "Order", TasksAdapter.onStatus.NEW.toString(),false,System.currentTimeMillis(),0))
        onNextSafe(AddTaskResult(newTask))
        onCompleteSafe()
    }

    private val updateTaskActionProcessor = createActionProcessor<UpdateTaskAction, HomeResult>(
        schedulersProvider,
        { InProgressResult },
        { t -> ErrorResult(t) }
    ) { action ->
        val task = taskRepository.getTask(action.taskId)?.copy(isDone = action.isDone)
        val updateTaskResult = if (task == null) {
            ErrorResult(Exception("Task with id ${action.taskId} not found in DB"))
        } else {
            taskRepository.updateTask(task)
            UpdateTaskResult(task)
        }
        onNextSafe(updateTaskResult)
        onCompleteSafe()
    }


    private val loadSingleTaskActionProcessor = createActionProcessor<LoadSingleTasksAction, HomeResult>(
        schedulersProvider,
        { InProgressResult },
        { t -> ErrorResult(t) }
    ) { action ->
        val task = taskRepository.getTask(action.taskId)?.copy()
        val singleTaskResult = if (task == null) {
            ErrorResult(Exception("Task with id ${action.taskId} not found in DB"))
        } else {
            LoadSingleTasksResult(task)
        }

        onNextSafe(singleTaskResult)
        onCompleteSafe()
    }




    private val updateStatusActionProcessor = createActionProcessor<UpdateStatusAction, HomeResult>(
        schedulersProvider,
        { InProgressResult },
        { t -> ErrorResult(t) }
    ) { action ->
        val task = taskRepository.getTask(action.taskId)?.copy(status = action.status)
        val updateTaskResult = if (task == null) {
            ErrorResult(Exception("Task with id ${action.taskId} not found in DB updateStatusActionProcessor"))
        } else {
            if(task.status == TasksAdapter.onStatus.DELIVERED.toString())
                task.deliveredDate =  System.currentTimeMillis()
            taskRepository.updateTask(task)
            UpdateTaskResult(task)
        }

        onNextSafe(updateTaskResult)
        onCompleteSafe()
    }


    private val DeleteStatusActionProcessor = createActionProcessor<DeleteStatusAction, HomeResult>(
        schedulersProvider,
        { InProgressResult },
        { t -> ErrorResult(t) }
    ) { action ->
        val task = taskRepository.getTask(action.taskId)?.copy(status = action.status)
        val updateTaskResult = if (task == null) {
            ErrorResult(Exception("Task with id ${action.taskId} not found in DB"))
        } else {
            taskRepository.deleteSingle(task)
            DeleteTaskResult(task)

        }

        onNextSafe(updateTaskResult)
        onCompleteSafe()
    }



    private val deletedCompletedTasksActionProcessor = createActionProcessor<DeleteCompletedTasksAction, HomeResult>(
        schedulersProvider,
        { InProgressResult},
        { t -> ErrorResult(t) }
    ) {
        val doneTasks = taskRepository.getAllDoneTasks(System.currentTimeMillis())
        taskRepository.delete(doneTasks)


        onNextSafe(LoadTasksResult(taskRepository.getAllTasks()))
        onCompleteSafe()
    }
}