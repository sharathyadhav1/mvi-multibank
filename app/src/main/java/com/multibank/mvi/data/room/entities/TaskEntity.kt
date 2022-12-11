package com.multibank.mvi.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "isDone") val isDone: Boolean,
    @ColumnInfo(name = "createdDate") val createdDate: Long,
    @ColumnInfo(name = "deliveredDate") val deliveredDate: Long
)