package com.liamfarrell.android.koganclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.databinding.ListItemShoppingCartItemBinding
import com.liamfarrell.android.koganclone.model.shoppingcart.ShoppingCartOrderItem
import com.liamfarrell.android.koganclone.ui.shoppingcart.ShoppingCartFragmentDirections


/**
 * Adapter for the trending products [RecyclerView] in [HomeFragment].
 */
class ShoppingCartListAdapter :
        ListAdapter<ShoppingCartOrderItem, ShoppingCartListAdapter.ViewHolder>(ShoppingCartItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.list_item_shopping_cart_item, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(item, onItemClick(item.item.productId, item.itemCount, item.item.title, item.item.imageUrl?.get(0) ))
            itemView.tag = item
        }
    }


    class ViewHolder(
            private val binding: ListItemShoppingCartItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShoppingCartOrderItem, onItemClickListener: View.OnClickListener) {
            with(binding) {
                orderItem = item
                binding.root.setOnClickListener(onItemClickListener)
                executePendingBindings()
            }
        }
    }

    private fun onItemClick(productId: Int, productCount: Int, title: String, imgUrl: String?) : View.OnClickListener{
        return View.OnClickListener {
            val action = ShoppingCartFragmentDirections.actionShoppingCartFragmentToEditOrderItemQuantityDialog(productId, productCount, title, imgUrl ?: "" )
            it.findNavController().navigate(action)
        }
    }



    private class ShoppingCartItemDiffCallback : DiffUtil.ItemCallback<ShoppingCartOrderItem>() {

        override fun areItemsTheSame(oldItem: ShoppingCartOrderItem, newItem: ShoppingCartOrderItem): Boolean {
            return oldItem.shopping_cart_id == newItem.shopping_cart_id
        }

        override fun areContentsTheSame(oldItem: ShoppingCartOrderItem, newItem: ShoppingCartOrderItem): Boolean {
            return oldItem == newItem
        }
    }

}


