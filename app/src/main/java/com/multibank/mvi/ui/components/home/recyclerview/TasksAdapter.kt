package com.multibank.mvi.ui.components.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.multibank.mvi.ui.model.Task


class TasksAdapter: RecyclerView.Adapter<TaskVieHolder>() {

    enum class onStatus {
        NEW, PREPARING, READY, DELIVERED,ONCLICK
    }


    var onClickListener : ((Long,onStatus,Task) -> Unit)? = null

    private var tasks = emptyList<Task>()

    fun updateList(newTasks: List<Task>) {
        val diffResult = DiffUtil.calculateDiff(TasksDiffCallback(tasks, newTasks))
        diffResult.dispatchUpdatesTo(this)
        this.tasks = newTasks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVieHolder {
        return TaskVieHolder(parent)
    }
    /*override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        )
    }*/

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskVieHolder, position: Int) {
        holder.bind(tasks[position], ::onClick)
    }

    private fun onClick(taskId: Long, status: onStatus,task: Task){
        onClickListener?.invoke(taskId,status,task)
    }

}
