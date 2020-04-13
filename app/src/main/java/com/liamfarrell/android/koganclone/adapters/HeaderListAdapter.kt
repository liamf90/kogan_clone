package com.liamfarrell.android.koganclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.databinding.HeaderListItemBinding
import com.liamfarrell.android.koganclone.model.homescreen.HomeScreenAd


/**
 * Adapter for the header [RecyclerView] in [HomeFragment].
 */
class HeaderListAdapter :
        ListAdapter<HomeScreenAd, HeaderListAdapter.ViewHolder>(HomeScreenAdDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.header_list_item, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resourceId = getItem(position)
        holder.apply {
            bind(resourceId)
            itemView.tag = resourceId
        }
    }


    class ViewHolder(
            private val binding: HeaderListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(homeScreenAd: HomeScreenAd) {
            with(binding) {
                this.homeScreenAd = homeScreenAd
                executePendingBindings()
            }
        }
    }

     class HomeScreenAdDiffCallback : DiffUtil.ItemCallback<HomeScreenAd>() {

        override fun areItemsTheSame(oldItem: HomeScreenAd, newItem: HomeScreenAd): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HomeScreenAd, newItem: HomeScreenAd): Boolean {
            return oldItem == newItem
        }
    }

}


