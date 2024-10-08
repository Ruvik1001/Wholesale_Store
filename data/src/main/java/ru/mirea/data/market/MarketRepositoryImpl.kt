package ru.mirea.data.market

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import ru.mirea.domain.market.data.CartItem
import ru.mirea.domain.market.MarketRepository
import ru.mirea.domain.market.data.Order
import ru.mirea.domain.market.data.OrderItem
import ru.mirea.domain.market.data.Product


class MarketRepositoryImpl: MarketRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun getCategories(): List<String> {
        val snapshot = database.child("catalog").get().await()
        return snapshot.children.mapNotNull { it.key }
    }

    override suspend fun getSubcategories(category: String): List<String> {
        val snapshot = database.child("catalog").child(category).child("subcategories").get().await()
        return snapshot.children.mapNotNull { it.key }
    }

    override suspend fun getProducts(category: String, subcategory: String): List<Product> {
        val snapshot = database.child("catalog").child(category).child("subcategories").child(subcategory).child("products").get().await()
        return snapshot.children.mapNotNull { it.getValue(Product::class.java) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun purchaseProduct(category: String, subcategory: String, productId: String, quantity: Int): Boolean {
        val productRef = database.child("catalog").child(category).child("subcategories").child(subcategory).child("products").child(productId)
        return kotlinx.coroutines.suspendCancellableCoroutine { continuation ->
            productRef.runTransaction(object : Transaction.Handler {
                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                    val product = mutableData.getValue(Product::class.java) ?: return Transaction.success(mutableData)
                    return if (product.count >= quantity) {
                        product.count -= quantity
                        mutableData.value = product
                        Transaction.success(mutableData)
                    } else {
                        Transaction.abort()
                    }
                }

                override fun onComplete(databaseError: DatabaseError?, committed: Boolean, dataSnapshot: DataSnapshot?) {
                    if (committed) {
                        continuation.resume(true) { /* Nothing to clean up */ }
                    } else {
                        continuation.resume(false) { /* Nothing to clean up */ }
                    }
                }
            })
        }
    }

    override suspend fun createOrder(cart: List<CartItem>): Boolean {
        val user = auth.currentUser ?: return false
        val orderRef = database.child("orders").push()
        val order = Order(
            userId = user.uid,
            userEmail = user.email ?: "",
            orderDate = getCurrentDateTime(),
            items = cart.map { item ->
                OrderItem(
                    productId = item.productId,
                    productName = item.productName,
                    quantity = item.quantity
                )
            }
        )

        return try {
            orderRef.setValue(order).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun searchProductsByName(query: String): List<Product> {
        val snapshot = database.child("catalog").get().await()
        val matchingProducts = mutableListOf<Product>()
        for (categorySnapshot in snapshot.children) {
            for (subcategorySnapshot in categorySnapshot.child("subcategories").children) {
                for (productSnapshot in subcategorySnapshot.child("products").children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    if (product != null && product.name.contains(query, ignoreCase = true)) {
                        matchingProducts.add(product)
                    }
                }
            }
        }
        return matchingProducts
    }

    private fun getCurrentDateTime(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }
}