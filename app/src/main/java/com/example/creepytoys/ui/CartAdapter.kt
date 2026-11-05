package com.example.creepytoys.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.creepytoys.R
import com.example.creepytoys.domain.model.ToyItem

class CartAdapter(
    private var cartItems: List<Pair<ToyItem, Int>>,
    private val onIncreaseClick: (ToyItem) -> Unit,
    private val onDecreaseClick: (ToyItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgToy: ImageView = view.findViewById(R.id.img_cart_toy)
        val tvName: TextView = view.findViewById(R.id.tv_cart_toy_name)
        val tvPrice: TextView = view.findViewById(R.id.tv_cart_toy_price)
        val tvQuantity: TextView = view.findViewById(R.id.tv_cart_item_quantity)
        val btnIncrease: ImageButton = view.findViewById(R.id.btn_increase)
        val btnDecrease: ImageButton = view.findViewById(R.id.btn_decrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val (toy, quantity) = cartItems[position]

        holder.tvName.text = toy.name
        holder.tvPrice.text = "$${toy.price * quantity}"
        holder.tvQuantity.text = quantity.toString()
        holder.imgToy.setImageResource(toy.imageResId)

        holder.btnIncrease.setOnClickListener { onIncreaseClick(toy) }
        holder.btnDecrease.setOnClickListener { onDecreaseClick(toy) }
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateData(newItems: List<Pair<ToyItem, Int>>) {
        cartItems = newItems
        notifyDataSetChanged()
    }
}