package com.liamfarrell.android.koganclone.model.shoppingcart

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.liamfarrell.android.koganclone.model.Product

@Entity(tableName = "shopping_cart")
data class ShoppingCartItem(
    @PrimaryKey(autoGenerate = true) val shopping_cart_id: Int? = null,
    @Embedded val item: Product
)
