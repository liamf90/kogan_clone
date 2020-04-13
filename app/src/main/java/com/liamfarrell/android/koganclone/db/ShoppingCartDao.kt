package com.liamfarrell.android.koganclone.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.liamfarrell.android.koganclone.model.Product
import com.liamfarrell.android.koganclone.model.shoppingcart.ShoppingCartOrderItem

/**
 * The Data Access Object for the [ShoppingCartOrderItem] class.
 */
@Dao
interface ShoppingCartDao {

    @Query("SELECT * FROM shopping_cart GROUP BY productId ORDER BY shopping_cart_id")
    fun getAllItems(): LiveData<List<ShoppingCartOrderItem>>

    @Query("SELECT * FROM shopping_cart  WHERE productId = :productId")
    suspend fun getProduct(productId: Int): ShoppingCartOrderItem

    @Query("UPDATE shopping_cart SET itemCount = :amount WHERE productId = :productId")
    suspend fun updateProductShoppingCartQuantity(productId: Int, amount: Int)


    @Query ("SELECT itemCount FROM shopping_cart WHERE productId = :productId")
    fun countProducts(productId: Int) : Int

    @Query("DELETE FROM shopping_cart WHERE productId = :productId")
    fun removeProductFromShoppingCart(productId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewItem(newItem: ShoppingCartOrderItem)

    @Transaction
    suspend fun addItem(newItem: Product){
        val countP = countProducts(newItem.productId)
        if (countP == 0){
            insertNewItem(ShoppingCartOrderItem(null, newItem, 1))
        } else {
            updateProductShoppingCartQuantity(newItem.productId, (countP + 1))
        }
    }

    @Query("SELECT SUM(itemCount) FROM shopping_cart")
     fun getCountAllShoppingCartItems(): LiveData<Int>
}