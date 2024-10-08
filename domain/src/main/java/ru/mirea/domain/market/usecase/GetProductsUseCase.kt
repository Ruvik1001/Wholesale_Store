package ru.mirea.domain.market.usecase

import ru.mirea.domain.market.MarketRepository
import ru.mirea.domain.market.data.Product

class GetProductsUseCase(private val marketRepository: MarketRepository) {
    suspend fun execute(category: String, subcategory: String): List<Product> {
        return marketRepository.getProducts(category, subcategory)
    }
}
