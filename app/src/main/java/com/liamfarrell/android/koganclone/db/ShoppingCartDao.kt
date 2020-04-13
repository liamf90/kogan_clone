package com.liamfarrell.android.koganclone.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.liamfarrell.android.koganclone.model.shoppingcart.ShoppingCartItem
import com.liamfarrell.android.koganclone.model.shoppingcart.ShoppingCartOrderItem

/**
 * The Data Access Object for the [ShoppingCartItem] class.
 */
@Dao
interface ShoppingCartDao {

    @Query("SELECT productId, title, price, oldPrice, brand, isKoganFirstEligible, freeShipping, imageUrl, COUNT(productId) AS itemCount FROM shopping_cart GROUP BY productId ORDER BY shopping_cart_id")
    fun getAllItems(): LiveData<List<ShoppingCartOrderItem>>

    @Query("SELECT * FROM shopping_cart  WHERE productId = :productId")
    suspend fun getProduct(productId: Int): ShoppingCartItem

    @Query("DELETE FROM shopping_cart WHERE shopping_cart_id IN (SELECT shopping_cart_id FROM shopping_cart WHERE productId = :productId ORDER BY shopping_cart_id DESC LIMIT :amount)")
    fun decreaseShoppingCartAmountForProduct(productId: Int, amount: Int)

    @Query ("SELECT COUNT(*) FROM shopping_cart WHERE productId = :productId")
    fun countProducts(productId: Int) : Int

    @Transaction
    suspend fun changeProductShoppingCartQuantity(productId: Int, newAmount: Int){
        val countCurrent = countProducts(productId)
        if (newAmount > countCurrent ) {
            val shoppingCartItem = getProduct(productId)
            for (i in 1..(newAmount - countCurrent)) {
                addItem(
                    ShoppingCartItem(
                        null,
                        shoppingCartItem.item
                    )
                )
            }
        } else if (newAmount < countCurrent){
            decreaseShoppingCartAmountForProduct(productId, (countCurrent - newAmount))
        }
    }

    @Query("DELETE FROM shopping_cart WHERE productId = :productId")
    fun removeProductFromShoppingCart(productId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(newItem: ShoppingCartItem)


    @Query("SELECT COUNT(*) FROM shopping_cart")
     fun getCountAllShoppingCartItems(): LiveData<Int>
}