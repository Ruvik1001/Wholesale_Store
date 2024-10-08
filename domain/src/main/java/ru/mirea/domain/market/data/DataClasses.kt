package ru.mirea.domain.market.data

// Модель для товара
data class Product(
    var name: String = "",
    var cost: Double = 0.0,
    var count: Int = 0,
    var description: String = ""
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
