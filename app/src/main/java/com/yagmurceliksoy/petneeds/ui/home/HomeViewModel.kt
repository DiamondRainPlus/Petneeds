package com.yagmurceliksoy.petneeds.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yagmurceliksoy.petneeds.common.Resource
import com.yagmurceliksoy.petneeds.data.model.ProductUI
import com.yagmurceliksoy.petneeds.data.repository.ProductRepository
import com.yagmurceliksoy.petneeds.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val productRepository: ProductRepository, application: Application
): BaseViewModel(application) {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState

    private var _salesState = MutableLiveData<SalesState>()
    val salesState: LiveData<SalesState>
        get() = _salesState

    init {
    }

    fun getProducts() {
        launch {
            _homeState.value = HomeState.Loading
            val result = productRepository.getProducts()

            when (result) {
                is Resource.Success -> {
                    _homeState.value = HomeState.Data(result.data)
                }

                is Resource.Error -> {
                    _homeState.value = HomeState.Error(result.throwable)
                }

                else -> {}
            }
        }
    }

    fun getSaleProducts() {
        launch {
            _salesState.value = SalesState.Loading
            val result = productRepository.getSaleProducts()

            when (result) {
                is Resource.Success -> {
                    _salesState.value = SalesState.Data(result.data)
                }

                is Resource.Error -> {
                    _salesState.value = SalesState.Error(result.throwable)
                }

                else -> {}
            }
        }
    }

    fun getProductsByCategory(category: String) {
        launch {
            _homeState.value = HomeState.Loading
            val result = productRepository.getProductsByCategory(category)

            when (result) {
                is Resource.Success -> {
                    _homeState.value = HomeState.Data(result.data)
                }

                is Resource.Error -> {
                    _homeState.value = HomeState.Error(result.throwable)
                }

                else -> {}
            }
        }
    }

    fun addProductToFav(product: ProductUI) {
        launch {
            productRepository.addProductToFav(product)
        }
    }
}

sealed interface HomeState {
    object Loading: HomeState
    data class Data(val productsResponse: List<ProductUI>): HomeState

    data class Error(val throwable: Throwable): HomeState
}

sealed interface SalesState {
    object Loading: SalesState
    data class Data(val productsResponse: List<ProductUI>): SalesState

    data class Error(val throwable: Throwable): SalesState
}