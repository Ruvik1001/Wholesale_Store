package ru.mirea.domain.market.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Модель для товара
data class Product(
    var code: String = "",
    var name: String = "",
    var cost: Double = 0.0,
    var count: Int = 0,
    var description: String = "",
    var photoUrl: String = "",
)

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var productId: String,
    var productName: String,
    var quantity: Int
)

// Модель для элемента корзины
data class CartItem(
    var productId: String,
    var productName: String,
    var quantity: Int
)

// Модель для заказа
data class Order(
    var userId: String = "",
    var userEmail: String = "",
    var orderDate: String = "",
    var items: List<OrderItem> = listOf()
)

// Модель для элемента заказа
data class OrderItem(
    var productId: String = "",
    var productName: String = "",
    var quantity: Int = 0
)
