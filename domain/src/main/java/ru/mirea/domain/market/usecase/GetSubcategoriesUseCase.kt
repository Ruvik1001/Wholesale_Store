package ru.mirea.domain.market.usecase

import ru.mirea.domain.market.MarketRepository

class GetSubcategoriesUseCase(private val marketRepository: MarketRepository) {
    suspend fun execute(category: String): List<String> {
        return marketRepository.getSubcategories(category)
    }
}
