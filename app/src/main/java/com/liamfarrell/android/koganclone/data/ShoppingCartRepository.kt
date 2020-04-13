package com.liamfarrell.android.koganclone.data

import com.liamfarrell.android.koganclone.db.ShoppingCartDao
import com.liamfarrell.android.koganclone.model.Product
import com.liamfarrell.android.koganclone.model.shoppingcart.ShoppingCartItem
import javax.inject.Inject

class ShoppingCartRepository @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao
) {
     fun getShoppingCartItems() = shoppingCartDao.getAllItems()

    suspend fun addProductToShoppingCart(product: Product){
        shoppingCartDao.addItem(
            ShoppingCartItem(
                item = product
            )
        )
    }


    fun getShoppingCartCount() = shoppingCartDao.getCountAllShoppingCartItems()


    suspend fun changeProductShoppingCartQuantity(productId: Int, newAmount: Int) {
        shoppingCartDao.changeProductShoppingCartQuantity(productId, newAmount)
    }

    suspend fun removeProductFromShoppingCart(productId: Int) {
        shoppingCartDao.removeProductFromShoppingCart(productId)
    }

}