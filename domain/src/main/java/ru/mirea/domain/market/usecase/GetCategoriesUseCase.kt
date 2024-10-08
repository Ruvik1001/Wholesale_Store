package ru.mirea.domain.market.usecase

import ru.mirea.domain.market.MarketRepository

class GetCategoriesUseCase(private val marketRepository: MarketRepository) {
    suspend fun execute(): List<String> {
        return marketRepository.getCategories()
    }
}
