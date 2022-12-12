package com.multibank.mvi.ui.model

import android.os.Parcelable
import com.multibank.mvi.data.room.entities.TaskEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val id: Long,
    val content: String,
    val status: String,
    val isDone: Boolean,
    val createdDate: Long,
    var deliveredDate: Long
): Parcelable

fun Task.toTaskEntity() = TaskEntity(
    id,
    content,
    status,
    isDone,
    createdDate,
    deliveredDate
)

fun TaskEntity.toTask() = Task(
    id,
    content,
    status,
    isDone,
    createdDate,
    deliveredDate
)