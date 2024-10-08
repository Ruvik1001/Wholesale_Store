package ru.mirea.domain.market.usecase

import ru.mirea.domain.market.MarketRepository

class PurchaseProductUseCase(private val marketRepository: MarketRepository) {
    suspend fun execute(category: String, subcategory: String, productId: String, quantity: Int): Boolean {
        return marketRepository.purchaseProduct(category, subcategory, productId, quantity)
    }
}
