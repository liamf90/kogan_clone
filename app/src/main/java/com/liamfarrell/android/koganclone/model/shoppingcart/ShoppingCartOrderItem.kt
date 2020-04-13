package com.liamfarrell.android.koganclone.model.shoppingcart

import androidx.room.Embedded
import com.liamfarrell.android.koganclone.model.Product

data class ShoppingCartOrderItem(
    @Embedded val item: Product,
    val itemCount: Int
)


