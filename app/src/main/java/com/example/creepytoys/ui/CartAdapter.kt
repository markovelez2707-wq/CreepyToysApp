package com.example.creepytoys.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.creepytoys.R
import com.example.creepytoys.domain.model.ToyItem
import java.util.Locale

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
        val btnIncrease: TextView = view.findViewById(R.id.btn_increase)
        val btnDecrease: TextView = view.findViewById(R.id.btn_decrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val (toy, quantity) = cartItems[position]

        holder.tvName.text = toy.name


        val itemSubtotal = toy.price * quantity

        holder.tvPrice.text = String.format(Locale.getDefault(), "$%.2f", itemSubtotal)
        holder.tvQuantity.text = quantity.toString()
        holder.imgToy.setImageResource(toy.imageResId)

        holder.btnDecrease.setOnClickListener {
            onDecreaseClick(toy)
        }

        holder.btnIncrease.setOnClickListener {
            onIncreaseClick(toy)
        }
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateData(newItems: List<Pair<ToyItem, Int>>) {
        cartItems = newItems
        notifyDataSetChanged()
    }
}