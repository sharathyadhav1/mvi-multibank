package com.multibank.mvi.data.repositories

import com.multibank.mvi.ui.model.Task


interface TaskRepository {

    fun addTask(task: Task): Task

    fun getTask(taskId: Long): Task?

    fun getAllTasks(): List<Task>

    fun getAllDoneTasks(): List<Task>

    fun updateTask(task: Task)

    fun delete(tasks: List<Task>)

    fun deleteSingle(tasks: Task)

}