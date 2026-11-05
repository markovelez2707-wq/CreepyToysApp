package com.example.creepytoys.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.creepytoys.R
import com.example.creepytoys.domain.model.ToyItem

class ToyAdapter(
    private var toyList: List<ToyItem>,
    private val onAddToCartClick: (ToyItem) -> Unit
) : RecyclerView.Adapter<ToyAdapter.ToyViewHolder>() {

    class ToyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgToy: ImageView = itemView.findViewById(R.id.img_toy)
        val tvName: TextView = itemView.findViewById(R.id.tv_toy_name)
        val tvFranchise: TextView = itemView.findViewById(R.id.tv_toy_franchise)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_toy_price)
        val btnAddToCart: Button = itemView.findViewById(R.id.btn_add_to_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_toy_card, parent, false)
        return ToyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToyViewHolder, position: Int) {
        val toy = toyList[position]

        holder.tvName.text = toy.name
        holder.tvFranchise.text = toy.franchise
        holder.tvPrice.text = "$${toy.price}"

        //IMAGEN EN LA PANTALLA
        holder.imgToy.setImageResource(toy.imageResId)

        // Accesibilidad (TalkBack)
        holder.imgToy.contentDescription = toy.contentDescription
        holder.btnAddToCart.contentDescription = "Agregar ${toy.name} al carrito por $${toy.price}"

        holder.btnAddToCart.setOnClickListener {
            onAddToCartClick(toy)
        }
    }

    override fun getItemCount(): Int = toyList.size

    fun updateList(newList: List<ToyItem>) {
        toyList = newList
        notifyDataSetChanged()
    }
}