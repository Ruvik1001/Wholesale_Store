package ru.mirea.basket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.basket.R
import ru.mirea.domain.market.data.CartItem

class BasketAdapter(
    private val cartItems: List<CartItem>,
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit
) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    inner class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvProductName: TextView = itemView.findViewById(R.id.productName)
        private val tvQuantity: TextView = itemView.findViewById(R.id.quantity)
        private val tvPrice: TextView = itemView.findViewById(R.id.price)
        private val btnIncrease: Button = itemView.findViewById(R.id.buttonIncrease)
        private val btnDecrease: Button = itemView.findViewById(R.id.buttonDecrease)

        fun bind(
            cartItem: CartItem,
        ) {
            tvProductName.text = cartItem.productName
            tvQuantity.text = cartItem.quantity.toString()
            tvPrice.text = (cartItem.productCost * cartItem.quantity).toString()
            btnIncrease.setOnClickListener { onIncrease(cartItem) }
            btnDecrease.setOnClickListener { onDecrease(cartItem) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.basket_item, parent, false)
        return BasketViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount() = cartItems.size



}