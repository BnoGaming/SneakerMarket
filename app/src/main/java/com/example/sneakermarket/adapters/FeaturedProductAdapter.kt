package com.example.sneakermarket.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sneakermarket.R
import com.example.sneakermarket.models.Product

class FeaturedProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<FeaturedProductAdapter.FeaturedViewHolder>() {

    class FeaturedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.itemFeaturedImage)
        val productName: TextView = view.findViewById(R.id.itemFeaturedName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_featured_product, parent, false)
        return FeaturedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.name

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.productImage)

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount() = products.size
}