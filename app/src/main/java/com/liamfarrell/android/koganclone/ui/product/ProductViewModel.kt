package com.liamfarrell.android.koganclone.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liamfarrell.android.koganclone.data.ProductRepository
import com.liamfarrell.android.koganclone.data.ShoppingCartRepository
import com.liamfarrell.android.koganclone.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val shoppingCartRepository: ShoppingCartRepository, private val productRepository: ProductRepository): ViewModel() {

    private val _spinner = MutableLiveData<Boolean>()
    val spinner = _spinner

    private val _caughtError = MutableLiveData<Exception>()
    val caughtError : LiveData<Exception> = _caughtError

    private val _product = MutableLiveData<Product>()
    val product : LiveData<Product> = _product

    fun addItemToShoppingCart(){
        viewModelScope.launch (Dispatchers.IO){
            product.value?.let{shoppingCartRepository.addProductToShoppingCart(it)}
        }
    }

    fun getProduct(productId: Int){
            viewModelScope.launch {
                _spinner.postValue(true)
                val productResult = productRepository.getProduct(productId)
                if (productResult.error != null){
                    _caughtError.postValue(productResult.error)
                } else {
                    _product.postValue(productResult.result)
                }
                _spinner.postValue(false)
            }
    }

}
