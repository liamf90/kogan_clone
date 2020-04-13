package com.liamfarrell.android.koganclone.data

import com.liamfarrell.android.koganclone.db.ShoppingCartDao
import com.liamfarrell.android.koganclone.model.Product
import javax.inject.Inject

class ShoppingCartRepository @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao
) {
     fun getShoppingCartItems() = shoppingCartDao.getAllItems()

    suspend fun addProductToShoppingCart(product: Product){
        shoppingCartDao.addItem(product)
    }


    fun getShoppingCartCount() = shoppingCartDao.getCountAllShoppingCartItems()


    suspend fun changeProductShoppingCartQuantity(productId: Int, newAmount: Int) {
        if (newAmount != 0){
            shoppingCartDao.updateProductShoppingCartQuantity(productId, newAmount)
        } else {
            shoppingCartDao.removeProductFromShoppingCart(productId)
        }

    }

    suspend fun removeProductFromShoppingCart(productId: Int) {
        shoppingCartDao.removeProductFromShoppingCart(productId)
    }

}