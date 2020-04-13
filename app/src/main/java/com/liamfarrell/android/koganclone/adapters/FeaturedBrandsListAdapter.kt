package com.liamfarrell.android.koganclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.databinding.ListItemFeaturedBrandsBinding
import com.liamfarrell.android.koganclone.model.homescreen.HomeScreenBrandAd


/**
 * Adapter for the trending products [RecyclerView] in [HomeFragment].
 */
class FeaturedBrandsListAdapter :
        ListAdapter<HomeScreenBrandAd, FeaturedBrandsListAdapter.ViewHolder>(HomeScreenBrandAdDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.list_item_featured_brands, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val homeScreenBrandAd = getItem(position)
        holder.apply {
            bind(homeScreenBrandAd)
            itemView.tag = homeScreenBrandAd
        }
    }


    class ViewHolder(
            private val binding: ListItemFeaturedBrandsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(homeScreenBrandAd: HomeScreenBrandAd) {
            with(binding) {
                this.homeScreenBrandAd = homeScreenBrandAd
                executePendingBindings()
            }
        }
    }

    private class HomeScreenBrandAdDiffCallback : DiffUtil.ItemCallback<HomeScreenBrandAd>() {

        override fun areItemsTheSame(oldItem: HomeScreenBrandAd, newItem: HomeScreenBrandAd): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HomeScreenBrandAd, newItem: HomeScreenBrandAd): Boolean {
            return oldItem == newItem
        }
    }

}


