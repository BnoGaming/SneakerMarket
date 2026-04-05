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

// RENAMED from ProductAdapter
class ProductGridAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductGridAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.itemProductImage)
        val productName: TextView = view.findViewById(R.id.itemProductName)
        val productPrice: TextView = view.findViewById(R.id.itemProductPrice)
        val productBrand: TextView = view.findViewById(R.id.itemProductBrand)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.name
        holder.productPrice.text = "$${product.price}"
        holder.productBrand.text = product.brand

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(holder.productImage)

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount() = products.size
}