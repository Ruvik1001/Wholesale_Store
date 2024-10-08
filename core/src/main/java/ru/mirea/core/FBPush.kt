package ru.mirea.core

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/*
* Данный файл необходим для начальной инициализации схемы в Fire Base
 */

// Полный аналог класса из домена
data class Product(
    var name: String = "",
    var cost: Double = 0.0,
    var count: Int = 0,
    var description: String = ""
)

// Полный аналог класса из домена
data class Category(
    var name: String = "",
    var subcategories: Map<String, Category>? = null,
    var products: Map<String, Product>? = null
)

fun createRealisticCatalogInFirebase() {
    // Получаем ссылку на корень базы данных
    val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("catalog")

    // Категории и подкатегории
    val catalogData = mapOf(
        "Электроника" to listOf("ПК и ноутбуки", "Бытовая техника", "Смартфоны и планшеты"),
        "Одежда" to listOf("Мужская одежда", "Женская одежда", "Детская одежда"),
        "Продукты питания" to listOf("Фрукты и овощи", "Молочные продукты", "Мясные изделия"),
        "Мебель" to listOf("Офисная мебель", "Мебель для дома", "Кухонная мебель")
    )

    var categoryIndex = 1
    for ((categoryName, subcategories) in catalogData) {
        val categoryRef = database.child(categoryName)

        var subcategoryIndex = 1
        for (subcategoryName in subcategories) {
            val subcategoryRef = categoryRef.child("subcategories").child(subcategoryName)

            // Добавляем 30 продуктов в подкатегорию
            addProductsToCategory(subcategoryRef, categoryIndex, subcategoryIndex, subcategoryName)
            subcategoryIndex++
        }
        categoryIndex++
    }
}

// Функция для добавления 30 продуктов в категорию или подкатегорию с реальными названиями
fun addProductsToCategory(categoryRef: DatabaseReference, categoryNum: Int, subcategoryNum: Int, subcategoryName: String) {
    val productNames = generateProductNames(subcategoryName)

    // Генерация 30 продуктов
    for ((index, productName) in productNames.withIndex()) {
        val productCode = generateProductCode(categoryNum, subcategoryNum, index + 1)
        val product = Product(
            name = productName,
            cost = (1000..50000).random().toDouble(), // Случайная цена от 1000 до 50000 рублей
            count = (1..100).random(), // Случайный остаток от 1 до 100
            description = "Описание продукта $productName на русском языке"
        )

        // Добавляем продукт в Firebase
        categoryRef.child("products").child(productCode).setValue(product)
    }
}

// Функция для генерации имен продуктов на основе подкатегории
fun generateProductNames(subcategoryName: String): List<String> {
    return when (subcategoryName) {
        "ПК и ноутбуки" -> listOf(
            "Игровой ПК Asus RX500", "Ноутбук Dell XPS 13", "Ноутбук MacBook Pro 16",
            "Монитор LG UltraWide", "ПК Lenovo ThinkCentre", "Ноутбук Acer Predator",
            // Добавить ещё продукты...
        )
        "Бытовая техника" -> listOf(
            "Холодильник Samsung", "Пылесос Dyson V11", "Микроволновка LG",
            "Стиральная машина Bosch", "Кондиционер Mitsubishi", "Тостер Philips",
            // Добавить ещё продукты...
        )
        "Смартфоны и планшеты" -> listOf(
            "Смартфон iPhone 14", "Планшет Samsung Galaxy Tab", "Смартфон Xiaomi Mi 11",
            "Смартфон Google Pixel 6", "Планшет iPad Pro", "Смартфон OnePlus 9",
            // Добавить ещё продукты...
        )
        "Мужская одежда" -> listOf(
            "Футболка Adidas", "Куртка Nike", "Брюки Levi's", "Джинсы Wrangler", "Костюм Hugo Boss",
            // Добавить ещё продукты...
        )
        "Женская одежда" -> listOf(
            "Платье Zara", "Куртка Mango", "Джинсы H&M", "Юбка Uniqlo", "Блузка Massimo Dutti",
            // Добавить ещё продукты...
        )
        "Детская одежда" -> listOf(
            "Детский комбинезон Reima", "Футболка Disney", "Шорты Gap Kids", "Куртка Nike Kids",
            // Добавить ещё продукты...
        )
        "Фрукты и овощи" -> listOf(
            "Яблоки Гренни Смит", "Бананы Эквадор", "Апельсины Марокко", "Помидоры Черри",
            // Добавить ещё продукты...
        )
        "Молочные продукты" -> listOf(
            "Молоко Простоквашино", "Творог Домик в деревне", "Сметана Савушкин", "Йогурт Danone",
            // Добавить ещё продукты...
        )
        "Мясные изделия" -> listOf(
            "Колбаса Докторская", "Говядина Мираторг", "Куриное филе Петруха", "Сосиски Велком",
            // Добавить ещё продукты...
        )
        "Офисная мебель" -> listOf(
            "Стол офисный IKEA", "Стул для офиса Herman Miller", "Шкаф для документов Kettler",
            // Добавить ещё продукты...
        )
        "Мебель для дома" -> listOf(
            "Диван раскладной Hoff", "Кровать с матрасом Askona", "Обеденный стол IKEA",
            // Добавить ещё продукты...
        )
        "Кухонная мебель" -> listOf(
            "Кухонный гарнитур Leroy Merlin", "Обеденный стул Hoff", "Столешница IKEA",
            // Добавить ещё продукты...
        )
        else -> listOf("Продукт без категории")
    }
}

// Генерация кода продукта формата **********
fun generateProductCode(categoryNum: Int, subcategoryNum: Int, productNum: Int): String {
    val categoryCode = categoryNum.toString().padStart(2, '0')
    val subcategoryCode = subcategoryNum.toString().padStart(2, '0')
    val productCode = productNum.toString().padStart(4, '0')

    return "$categoryCode$subcategoryCode$productCode"
}
