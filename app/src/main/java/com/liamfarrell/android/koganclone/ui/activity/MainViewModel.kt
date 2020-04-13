package com.liamfarrell.android.koganclone.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liamfarrell.android.koganclone.data.ShoppingCartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel used in [MainActivity].
 */
class MainViewModel @Inject constructor(val shoppingCartRepository: ShoppingCartRepository): ViewModel() {

   val shoppingCartCount: LiveData<Int> = shoppingCartRepository.getShoppingCartCount()



   fun changeProductShoppingCartQuantity(productId: Int, newAmount: Int){
      viewModelScope.launch(Dispatchers.IO) {
         shoppingCartRepository.changeProductShoppingCartQuantity(productId, newAmount)
      }
   }


   fun removeProductFromShoppingCart(productId: Int){
      viewModelScope.launch (Dispatchers.IO) {
         shoppingCartRepository.removeProductFromShoppingCart(productId)
      }
   }

}