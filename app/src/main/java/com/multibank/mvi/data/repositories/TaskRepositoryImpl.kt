package com.multibank.mvi.data.repositories

import com.multibank.mvi.data.room.dao.TaskDao
import com.multibank.mvi.ui.model.Task
import com.multibank.mvi.ui.model.toTask
import com.multibank.mvi.ui.model.toTaskEntity


class TaskRepositoryImpl(
    private val taskDao: TaskDao
): TaskRepository {

    override fun addTask(task: Task): Task {
        val id = taskDao
            .insert(task.toTaskEntity())
        return task.copy(id = id)
    }

    override fun getTask(taskId: Long): Task? {
        return taskDao
            .getForId(taskId)
            .firstOrNull()
            ?.toTask()
    }

    override fun getAllTasks(): List<Task> {
        return taskDao
            .getAll()
            .map { it.toTask() }
    }

    override fun getAllDoneTasks(time: Long): List<Task> {
        return taskDao
            .getAllDone(time)
            .map { it.toTask() }
    }

    override fun updateTask(task: Task) {
        taskDao.update(task.toTaskEntity())
    }

    override fun delete(tasks: List<Task>) {
        taskDao
            .delete(tasks.map { it.toTaskEntity() })
    }

    override fun deleteSingle(task: Task) {
        taskDao
            .deleteSingle(task.toTaskEntity())
    }


}