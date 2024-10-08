package ru.mirea.domain.market.usecase

import ru.mirea.domain.market.MarketRepository
import ru.mirea.domain.market.data.Product

class SearchProductsByNameUseCase(private val marketRepository: MarketRepository) {
    suspend fun execute(query: String): List<Product> {
        return marketRepository.searchProductsByName(query)
    }
}
