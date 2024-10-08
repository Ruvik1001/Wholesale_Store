package ru.mirea.catalog.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mirea.catalog.CatalogRouter
import ru.mirea.domain.market.data.CartItem
import ru.mirea.domain.market.data.Product
import ru.mirea.domain.market.usecase.AddCartItemUseCase
import ru.mirea.domain.market.usecase.GetCategoriesUseCase
import ru.mirea.domain.market.usecase.GetProductsUseCase
import ru.mirea.domain.market.usecase.GetSubcategoriesUseCase
import ru.mirea.domain.market.usecase.SearchProductsByNameUseCase

class CatalogViewModel(
    private val catalogRouter: CatalogRouter,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSubcategoriesUseCase: GetSubcategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsByNameUseCase: SearchProductsByNameUseCase,
    private val addCartItemUseCase: AddCartItemUseCase,
) : ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    private val _subcategories = MutableLiveData<List<String>>()
    val subcategories: LiveData<List<String>> get() = _subcategories

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _searchResults = MutableLiveData<List<Product>>()
    val searchResults: LiveData<List<Product>> get() = _searchResults

    fun lunchProfile() {
        catalogRouter.goToProfile()
    }

    fun lunchBasket() {
        catalogRouter.goToBasket()
    }

    fun loadCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = getCategoriesUseCase.execute()
            withContext(Dispatchers.Main) {
                _categories.value = result
            }
        }
    }

    fun loadSubcategories(category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = getSubcategoriesUseCase.execute(category)
            withContext(Dispatchers.Main) {
                _subcategories.value = result
            }
        }
    }

    fun loadProducts(category: String, subcategory: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = getProductsUseCase.execute(category, subcategory)
            withContext(Dispatchers.Main) {
                _products.value = result
            }
        }
    }

    fun searchProductsByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = searchProductsByNameUseCase.execute(query)
            withContext(Dispatchers.Main) {
                _searchResults.value = result
            }
        }
    }

    fun addCartItem(cartItem: CartItem) {
        CoroutineScope(Dispatchers.IO).launch {
            addCartItemUseCase.invoke(cartItem)
        }
    }
}
