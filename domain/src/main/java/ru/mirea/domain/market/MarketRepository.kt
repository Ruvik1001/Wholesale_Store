package ru.mirea.domain.market

import ru.mirea.domain.market.data.CartItem
import ru.mirea.domain.market.data.Product

interface MarketRepository {
    suspend fun getCategories(): List<String>
    suspend fun getSubcategories(category: String): List<String>
    suspend fun getProducts(category: String, subcategory: String): List<Product>
    suspend fun purchaseProduct(category: String, subcategory: String, productId: String, quantity: Int): Boolean
    suspend fun createOrder(cart: List<CartItem>): Boolean
    suspend fun searchProductsByName(query: String): List<Product>
}
