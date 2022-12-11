package com.multibank.mvi.ui.components.home.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.multibank.mvi.R
import com.multibank.mvi.databinding.ItemTaskBinding
import com.multibank.mvi.ui.model.Task
import com.multibank.mvi.utils.inflateItem
import kotlinx.android.extensions.LayoutContainer



class TaskVieHolder(
    parent: ViewGroup,
    override val containerView: View = parent.inflateItem(R.layout.item_task)
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: Task,onClickListener: ((Long, TasksAdapter.onStatus,Task) -> Unit)) {
        val bind = ItemTaskBinding.bind(itemView)

        bind.apply {

            status.setOnClickListener {

                if (status.text == TasksAdapter.onStatus.NEW.toString()) {
                    status.text = TasksAdapter.onStatus.PREPARING.toString()
                    onClickListener(item.id, TasksAdapter.onStatus.PREPARING,item)
                } else if (status.text == TasksAdapter.onStatus.PREPARING.toString()) {
                    status.text = TasksAdapter.onStatus.READY.toString()
                    onClickListener(item.id, TasksAdapter.onStatus.READY,item)
                } else if (status.text == TasksAdapter.onStatus.READY.toString()) {
                    status.text = TasksAdapter.onStatus.DELIVERED.toString()
                    onClickListener(item.id, TasksAdapter.onStatus.DELIVERED,item)
                }

            }
            status.text = item.status
            taskContent.text ="${"Order"} ${"-"} ${item.id}"
            rowItem.setOnClickListener {
                onClickListener(item.id, TasksAdapter.onStatus.ONCLICK,item)

            }


        }

    }
}