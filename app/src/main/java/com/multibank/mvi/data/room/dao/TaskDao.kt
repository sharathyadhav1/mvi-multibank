package com.multibank.mvi.data.room.dao

import androidx.room.*
import com.multibank.mvi.data.room.entities.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAll(): List<TaskEntity>


    @Query("SELECT * FROM task where status = 'DELIVERED' and deliveredDate< (:current_time-15000)")
    fun getAllDone(current_time: Long ): List<TaskEntity>

    @Query("SELECT * FROM task WHERE id IN (:id)")
    fun getForId(id: Long): List<TaskEntity>

    @Query("SELECT COUNT(*) FROM task")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: TaskEntity): Long

    @Update
    fun update(task: TaskEntity)

    @Delete
    fun delete(tasks: List<TaskEntity>)

    @Delete
    fun deleteSingle(tasks: TaskEntity)

    @Query("DELETE FROM task")
    fun deleteAll()
}