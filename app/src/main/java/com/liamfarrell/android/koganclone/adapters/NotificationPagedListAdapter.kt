package com.liamfarrell.android.koganclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.databinding.ListItemNotificationBinding
import com.liamfarrell.android.koganclone.model.notification.Notification

/**
 * Adapter for the [RecyclerView] in [NotificationsFragment].
 */
class NotificationPagedListAdapter :
    PagedListAdapter<Notification, NotificationPagedListAdapter.ViewHolder>(NotificationDiffCallback()) {

    override fun getItemId(position: Int): Long {
        return super.getItem(position)?.id?.toLong() ?: 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_notification, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = getItem(position)
        //notification?.let{onNotificationLoadedByAdapter(it.getNotification())}
        holder.apply {
            notification?.let {
                bind(it)
                itemView.tag = it
                itemView.setOnClickListener(getItemOnClickListener(it))
            }
        }
    }

    private fun getItemOnClickListener(notification: Notification) : View.OnClickListener{
        return View.OnClickListener {
            //it.findNavController().navigate(direction)
        }
    }


    class ViewHolder(
        private val binding: ListItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Notification) {
            with(binding) {
                notification = item
                executePendingBindings()
            }
        }
    }
}


private class NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {

    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }
}