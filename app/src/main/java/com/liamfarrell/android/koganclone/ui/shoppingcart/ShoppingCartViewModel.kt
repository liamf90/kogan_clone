package com.liamfarrell.android.koganclone.ui.shoppingcart

import androidx.lifecycle.*
import com.liamfarrell.android.koganclone.api.KoganApiService
import com.liamfarrell.android.koganclone.data.ShoppingCartRepository
import com.liamfarrell.android.koganclone.model.shoppingcart.ShoppingCartOrderItem
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * The ViewModel used in [ShoppingCartFragment].
 */
class ShoppingCartViewModel @Inject constructor(shoppingCartRepository: ShoppingCartRepository, val koganApiService: KoganApiService) : ViewModel() {

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

    //initialising boolean for observers to know whether to update total price.
    val _initialising = MutableLiveData(true)
    val initialising: LiveData<Boolean> = _initialising


    init {
        freightProtectionSelected = MutableLiveData(false)
        tryKoganFirstSelected = MutableLiveData(false)
        _initialising.value = false


        totalPrice.addSource(freightProtectionSelected){
            if (_initialising.value == false){
                viewModelScope.launch (Dispatchers.Main){
                    _updateInProgress.value = true
                    delay(400)
                    calculateTotalPrice()
                    _updateInProgress.value = false
                }

            }
        }

        totalPrice.addSource(tryKoganFirstSelected) {
            if (_initialising.value == false) {
                //update delivery price
                updateDeliveryPrice()
            }
        }

        totalPrice.addSource(items){
                viewModelScope.launch{
                    updateDeliveryAndFreightProtectionAndTotalPrice()
                }
        }
    }


    private fun calculateTotalPrice(){
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
        totalPrice.value = tv
    }

    private suspend fun updateDeliveryAndFreightProtectionAndTotalPrice(){
        withContext(Dispatchers.Main) {
            _updateInProgress.value = true
            val deliveryPriceDeferred = async { getDeliveryPrice() }
            val freightProtectionPriceDeferred = async { getFreightProtectionPrice() }
            val deliveryPrice = deliveryPriceDeferred.await()
            val freightProtectPrice = freightProtectionPriceDeferred.await()
            _deliveryPrice.value = deliveryPrice
            _freightProtectionPrice.value = freightProtectPrice
            calculateTotalPrice()
            _updateInProgress.value = false
        }
    }

    private fun updateDeliveryPrice() {
        viewModelScope.launch (Dispatchers.Main) {
            _updateInProgress.value = true
            _deliveryPrice.value = getDeliveryPrice()
            calculateTotalPrice()
            _updateInProgress.value = false
        }
    }

    private suspend fun getDeliveryPrice() : Double? {
        if (tryKoganFirstSelected.value == false){
            return withContext(Dispatchers.IO) {
                delay(400)
                //All items
                val allItemsList = mutableListOf<Int>()
                items.value?.forEach {
                    for (i in 1..it.itemCount) {
                        allItemsList.add(it.item.productId)
                    }
                }
                val deliveryPriceCall = koganApiService.getDeliveryPrice(allItemsList).execute()
                val deliveryPrice = deliveryPriceCall.body()?.delivery_cost
                return@withContext deliveryPrice
            }
        }
        else if (tryKoganFirstSelected.value == true &&  items.value?.filter { !it.item.isKoganFirstEligible }?.size != 0){
            return withContext(Dispatchers.IO) {
                //Only items not Kogan first eligible items
                val allItemsList = mutableListOf<Int>()
                 items.value?.filter { !it.item.isKoganFirstEligible }?.forEach {
                    for (i in 0..it.itemCount) {
                        allItemsList.add(it.item.productId)
                    }
                }
                val deliveryPrice = koganApiService.getDeliveryPrice(allItemsList).execute().body()?.delivery_cost
                return@withContext deliveryPrice
            }
        } else {
            delay(300)
            return 0.00
        }
    }

    private suspend fun getFreightProtectionPrice() : Double? {
        return withContext(Dispatchers.IO) {
            val allItemsList = mutableListOf<Int>()
            items.value?.forEach {
                for (i in 1..it.itemCount) {
                    allItemsList.add(it.item.productId)
                }
            }
            val freightProtectionCall = koganApiService.getFreightProtectionPrice(allItemsList).execute()
            val freightProtectionPrice = freightProtectionCall.body()?.freight_protection_cost
            return@withContext freightProtectionPrice
        }
     }



    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}


