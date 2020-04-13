package com.liamfarrell.android.koganclone.ui.shoppingcart

import androidx.lifecycle.*
import com.liamfarrell.android.koganclone.api.KoganApiService
import com.liamfarrell.android.koganclone.data.DeliveryRepository
import com.liamfarrell.android.koganclone.data.ShoppingCartRepository
import com.liamfarrell.android.koganclone.model.Product
import com.liamfarrell.android.koganclone.model.delivery.DeliveryPriceResult
import com.liamfarrell.android.koganclone.model.shoppingcart.ShoppingCartOrderItem
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * The ViewModel used in [ShoppingCartFragment].
 */
class ShoppingCartViewModel @Inject constructor(shoppingCartRepository: ShoppingCartRepository, val koganApiService: KoganApiService, val deliveryRepository: DeliveryRepository) : ViewModel() {

    private val _caughtError = MutableLiveData<Exception>()
    val caughtError : LiveData<Exception> = _caughtError

    //initialising boolean for observers to know whether to update total price.
    var inititialising = true

    val _updatingTotalPrice = MutableLiveData(true)
    val updatingTotalPrice: LiveData<Boolean> = _updatingTotalPrice

    private val _updateInProgress = MutableLiveData<Boolean>(false)
    val updateInProgress : LiveData<Boolean> = _updateInProgress

    private val _deliveryPrice = MutableLiveData<Double>(null)
    val deliveryPrice : LiveData<Double?> = _deliveryPrice

    private val _freightProtectionPrice = MediatorLiveData<Double?>()
    val freightProtectionPrice : LiveData<Double?> = _freightProtectionPrice

    val tryKoganFirstSelected : MutableLiveData<Boolean>
    val freightProtectionSelected : MutableLiveData<Boolean>

    val items : LiveData<List<ShoppingCartOrderItem>> = shoppingCartRepository.getShoppingCartItems()

    val totalPrice = MediatorLiveData<Double>()




    init {
        freightProtectionSelected = MutableLiveData(false)
        tryKoganFirstSelected = MutableLiveData(false)
        _updatingTotalPrice.value = false


        totalPrice.addSource(freightProtectionSelected){
            if (!inititialising){
                viewModelScope.launch (Dispatchers.Main){
                    _updateInProgress.value = true
                    delay(400)
                    calculateTotalPrice()
                    _updateInProgress.value = false
                }
            }
        }

        totalPrice.addSource(tryKoganFirstSelected) {
            if (!inititialising) {
                viewModelScope.launch {
                    updateDeliveryPrice()
                }
            }
        }

        totalPrice.addSource(items){
            inititialising = false
                viewModelScope.launch {

                        _updatingTotalPrice.value = true
                        updateDeliveryAndFreightProtectionAndTotalPrice()
                        _updatingTotalPrice.value = false

                }
        }
    }


    private suspend fun calculateTotalPrice(){
        withContext(Dispatchers.IO){
            //update total price
            val freightProtectionPriceAddedToTotal =
                when (freightProtectionSelected.value) {
                    true -> freightProtectionPrice.value ?: 0.00
                    false -> 0.00
                    null -> 0.00
                }

            val itemsTotalPrice = items.value?.map { it.itemCount *  it.item.price }?.sum() ?: 0.00
            val deliveryPrice = _deliveryPrice.value ?: 0.00
            val tv = itemsTotalPrice + deliveryPrice + freightProtectionPriceAddedToTotal
            totalPrice.postValue(tv)
        }

    }

    private suspend fun updateDeliveryAndFreightProtectionAndTotalPrice(){
        withContext(Dispatchers.Main) {
            _updateInProgress.value = true
            val deliveryPriceDeferred = async { getDeliveryPrice() }
            val freightProtectionPriceDeferred = async { getFreightProtectionPrice() }
            val deliveryPriceResult = deliveryPriceDeferred.await()
            val freightProtectPriceResult = freightProtectionPriceDeferred.await()

            if (deliveryPriceResult.error != null){
                _caughtError.value = deliveryPriceResult.error
            } else {
                _deliveryPrice.value = deliveryPriceResult.price
            }

            if (freightProtectPriceResult.error != null){
                _caughtError.value = freightProtectPriceResult.error
            } else {
                _freightProtectionPrice.value = freightProtectPriceResult.price
            }

            calculateTotalPrice()
            _updateInProgress.value = false
        }
    }

    private fun updateDeliveryPrice() {
        viewModelScope.launch (Dispatchers.Main) {
            _updateInProgress.value = true
            val deliveryPriceResult = getDeliveryPrice()
            if (deliveryPriceResult.error != null){
                _caughtError.value = deliveryPriceResult.error
            } else {
                _deliveryPrice.value = deliveryPriceResult.price
            }

            calculateTotalPrice()
            _updateInProgress.value = false
        }
    }

    private suspend fun getDeliveryPrice() : DeliveryPriceResult {
        val productList = mutableListOf<Product>()
        items.value?.forEach {
            for (i in 1..it.itemCount){
                productList.add(it.item)
            }
        }
        return deliveryRepository.getDeliveryPrice(productList, tryKoganFirstSelected.value ?: false)
    }

    private suspend fun getFreightProtectionPrice() : DeliveryPriceResult {
        val productList = mutableListOf<Product>()
        items.value?.forEach {
            for (i in 1..it.itemCount){
                productList.add(it.item)
            }
        }
        return deliveryRepository.getFreightProtectionPrice(productList)
     }



    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}


