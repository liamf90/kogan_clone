package com.liamfarrell.android.koganclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.databinding.ListItemTrendingProductBinding
import com.liamfarrell.android.koganclone.model.Product
import com.liamfarrell.android.koganclone.model.trendingproducts.TrendingProductDb
import com.liamfarrell.android.koganclone.ui.home.HomeFragmentDirections


/**
 * Adapter for the trending products [RecyclerView] in [HomeFragment].
 */
class TrendingProductsPagedListAdapter :
    PagedListAdapter<TrendingProductDb, TrendingProductsPagedListAdapter.ViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_trending_product, parent, false
            )
        )
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trendingProduct = getItem(position)
        holder.apply {
            trendingProduct?.let{
                bind(it.product)
                itemView.tag = it.product
                itemView.setOnClickListener(getOnClickListener(it.product.productId))
            }
        }
    }



    class ViewHolder(
        private val binding: ListItemTrendingProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                this.product = product
                executePendingBindings()
            }
        }
    }

    private fun getOnClickListener(productId: Int): View.OnClickListener {
        return View.OnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProductFragment(productId)
            it.findNavController().navigate(action)
        }
    }

    private class ProductDiffCallback : DiffUtil.ItemCallback<TrendingProductDb>() {

        override fun areItemsTheSame(oldItem: TrendingProductDb, newItem: TrendingProductDb): Boolean {
            return oldItem.position_index == newItem.position_index
        }

        override fun areContentsTheSame(oldItem: TrendingProductDb, newItem: TrendingProductDb): Boolean {
            return oldItem == newItem
        }
    }



}


