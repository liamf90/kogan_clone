package com.liamfarrell.android.koganclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.databinding.ListItemCurrentDealsAndPopularCategoriesBinding
import com.liamfarrell.android.koganclone.model.homescreen.HomeScreenAd


/**
 * Adapter for the trending products [RecyclerView] in [HomeFragment].
 */
class CurrentDealsAndPopularCategoriesListAdapter :
        ListAdapter<HomeScreenAd, CurrentDealsAndPopularCategoriesListAdapter.ViewHolder>(
            HeaderListAdapter.HomeScreenAdDiffCallback()
        ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.list_item_current_deals_and_popular_categories, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val homeScreenAd = getItem(position)
        holder.apply {
            bind(homeScreenAd)
            itemView.tag = homeScreenAd
            itemView.setOnClickListener(getOnClickListener(homeScreenAd))
        }
    }


    class ViewHolder(
            private val binding: ListItemCurrentDealsAndPopularCategoriesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(homeScreenAd: HomeScreenAd) {
            with(binding) {
                this.homeScreenAd = homeScreenAd
                executePendingBindings()
            }
        }
    }

    private fun getOnClickListener(homeScreenAd: HomeScreenAd): View.OnClickListener {
        return View.OnClickListener {
            //it.findNavController().navigate(R.id.productFragment)
        }
    }

}


