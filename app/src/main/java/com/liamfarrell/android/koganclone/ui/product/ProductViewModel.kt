package com.liamfarrell.android.koganclone.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liamfarrell.android.koganclone.api.KoganApiService
import com.liamfarrell.android.koganclone.data.ShoppingCartRepository
import com.liamfarrell.android.koganclone.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val shoppingCartRepository: ShoppingCartRepository, private val koganApiService: KoganApiService): ViewModel() {

    private val _spinner = MutableLiveData<Boolean>()
    val spinner = _spinner

    private val _product = MutableLiveData<Product>()
    val product : LiveData<Product> = _product

    fun addItemToShoppingCart(){
        viewModelScope.launch (Dispatchers.IO){
            product.value?.let{shoppingCartRepository.addProductToShoppingCart(it)}
        }
    }

    fun getProduct(productId: Int){
        viewModelScope.launch (Dispatchers.IO){
            _spinner.postValue(true)
            val p = koganApiService.getProduct(productId).execute().body()
            _spinner.postValue(false)
            _product.postValue(p)
        }


    }

}
