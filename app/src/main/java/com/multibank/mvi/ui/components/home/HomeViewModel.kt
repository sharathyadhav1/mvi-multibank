package com.multibank.mvi.ui.components.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.multibank.mvi.ui.base.mvi.MviViewModel
import com.multibank.mvi.ui.components.home.mvi.HomeAction
import com.multibank.mvi.ui.components.home.mvi.HomeAction.AddTaskAction
import com.multibank.mvi.ui.components.home.mvi.HomeAction.DeleteCompletedTasksAction
import com.multibank.mvi.ui.components.home.mvi.HomeAction.LoadTasksAction
import com.multibank.mvi.ui.components.home.mvi.HomeAction.UpdateTaskAction
import com.multibank.mvi.ui.components.home.mvi.HomeAction.UpdateStatusAction
import com.multibank.mvi.ui.components.home.mvi.HomeAction.LoadSingleTasksAction
import com.multibank.mvi.ui.components.home.mvi.HomeAction.DeleteStatusAction
import com.multibank.mvi.ui.components.home.mvi.HomeActionProcessor
import com.multibank.mvi.ui.components.home.mvi.HomeResult
import com.multibank.mvi.ui.components.home.recyclerview.TasksAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeViewModel(savedStateHandle : SavedStateHandle): MviViewModel<HomeAction, HomeResult, HomeViewState>(
    savedStateHandle,
    HomeActionProcessor(),
    HomeViewState.default()
) {
    
    fun loadDataIfNeeded() {
        if (viewState.tasks == null) {
            accept(LoadTasksAction)
        }
    }

    fun loadSingleTask(taskId: Long){
        accept(LoadSingleTasksAction(taskId))
    }
    
    fun addTask(taskContent: String) {
        if (!taskContent.isBlank()) {
            accept(AddTaskAction(taskContent))
        }
    }

    fun deleteCompletedTasks() {
        accept(DeleteCompletedTasksAction)
    }

    fun updateTask(taskId: Long, isDone: Boolean) {
        accept(UpdateTaskAction(taskId, isDone))
    }
    fun updateStatus(taskId: Long, status: String) {
        when(status){
            TasksAdapter.onStatus.DELIVERED.toString()->{
                accept(UpdateStatusAction(taskId, status))
                viewModelScope.launch {
                    delay(1000L*15)
                    accept(DeleteStatusAction(taskId, status))
                }
            }
            else->{
                accept(UpdateStatusAction(taskId, status))
            }

        }

    }


    fun updateDeliveredTask() {
        if(viewState.tasks!=null)
        {
            var is_completed_called= false
            for (task in viewState.tasks!!) {

                if(task.status == TasksAdapter.onStatus.DELIVERED.toString())
                {
                    if(task.deliveredDate>0 && task.deliveredDate<System.currentTimeMillis()-15000)
                    {
                        if(!is_completed_called)
                        {
                            is_completed_called =true;
                            accept(DeleteCompletedTasksAction)
                        }

                    }
                    else
                    {
                        viewModelScope.launch {
                            delay(15000 - (System.currentTimeMillis()-task.deliveredDate) )
                            accept(DeleteStatusAction(task.id, TasksAdapter.onStatus.DELIVERED.toString()))

                        }
                    }
                }


            }
        }

    }

}