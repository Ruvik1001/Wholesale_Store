package ru.mirea.domain.market.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.mirea.domain.market.data.CartItemEntity


@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cartItemEntity: CartItemEntity)

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    fun getCartItemById(productId: String): CartItemEntity?

    @Update
    fun update(cartItemEntity: CartItemEntity)

    @Delete
    fun delete(cartItemEntity: CartItemEntity)

    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Query("DELETE FROM cart_items")
    fun clearCart()
}
