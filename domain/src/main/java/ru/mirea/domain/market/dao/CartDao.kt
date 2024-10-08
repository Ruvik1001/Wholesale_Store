package ru.mirea.domain.market.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow
import ru.mirea.domain.market.data.CartItemEntity


@Dao
interface CartDao {

    // Получение всех товаров в корзине
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItemEntity>> // Используем Flow для асинхронного получения данных

    // Очистка корзины
    @Query("DELETE FROM cart_items")
    fun clearCart() // Suspend-функция для удаления всех элементов

    // Вставка товара
    @Insert
    fun insert(cartItem: CartItemEntity) // Suspend-функция для вставки

    // Удаление товара
    @Delete
    fun delete(cartItem: CartItemEntity) // Suspend-функция для удаления
}