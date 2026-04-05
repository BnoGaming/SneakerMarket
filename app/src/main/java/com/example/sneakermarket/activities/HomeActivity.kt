package com.example.sneakermarket.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakermarket.R
import com.example.sneakermarket.adapters.FeaturedProductAdapter
import com.example.sneakermarket.models.Product
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {

    private lateinit var featuredRecyclerView: RecyclerView
    private lateinit var featuredAdapter: FeaturedProductAdapter

    // Category Cards
    private lateinit var cardNike: CardView
    private lateinit var cardAdidas: CardView
    private lateinit var cardJordan: CardView
    private lateinit var cardNewBalance: CardView
    private lateinit var browseAllButton: MaterialButton

    private val featuredProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Find Views
        featuredRecyclerView = findViewById(R.id.featuredRecyclerView)
        cardNike = findViewById(R.id.cardNike)
        cardAdidas = findViewById(R.id.cardAdidas)
        cardJordan = findViewById(R.id.cardJordan)
        cardNewBalance = findViewById(R.id.cardNewBalance)
        browseAllButton = findViewById(R.id.browseAllButton)

        setupFeaturedList()

        setupCategoryClicks()
    }

    private fun setupFeaturedList() {
        // Manually add some featured products
        featuredProducts.apply {
            val allSizes = listOf(9.0, 9.5, 10.0)
            add(Product(1, "Nike Dunk Low 'Civilist'", "Nike", "Skateboarding", 450.0, "https://images.pexels.com/photos/6050923/pexels-photo-6050923.jpeg", allSizes, "Black", 4.8, isFeatured = true))
            add(Product(3, "Yeezy Boost 350 'Zebra'", "Adidas", "Lifestyle", 550.0, "https://images.pexels.com/photos/6050909/pexels-photo-6050909.jpeg", allSizes, "White", 4.7, isFeatured = true))
            add(Product(4, "Travis Scott x Fragment Jordan 1", "Jordan", "Basketball", 2800.0, "https://images.pexels.com/photos/7543639/pexels-photo-7543639.jpeg", allSizes, "Blue", 4.9, isFeatured = true))
            add(Product(10, "Air Jordan 4 'Military Black'", "Jordan", "Basketball", 410.0, "https://images.pexels.com/photos/12767786/pexels-photo-12767786.jpeg", allSizes, "White", 4.8, isFeatured = true))
        }

        featuredAdapter = FeaturedProductAdapter(featuredProducts) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("PRODUCT_ID", product.id)
                putExtra("PRODUCT_NAME", product.name)
                putExtra("PRODUCT_BRAND", product.brand)
                putExtra("PRODUCT_TYPE", product.type)
                putExtra("PRODUCT_PRICE", product.price)
                putExtra("PRODUCT_IMAGE", product.imageUrl)
                putExtra("PRODUCT_COLOR", product.color)
                putExtra("PRODUCT_SIZES", product.availableSizes.toCollection(ArrayList()))
            }
            startActivity(intent)
        }

        featuredRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        featuredRecyclerView.adapter = featuredAdapter
    }

    private fun setupCategoryClicks() {
        cardNike.setOnClickListener {
            launchProductList(brand = "Nike")
        }
        cardAdidas.setOnClickListener {
            launchProductList(brand = "Adidas")
        }
        cardJordan.setOnClickListener {
            launchProductList(brand = "Jordan")
        }
        cardNewBalance.setOnClickListener {
            launchProductList(brand = "New Balance")
        }
        browseAllButton.setOnClickListener {
            launchProductList() // No filters
        }

    }

    private fun launchProductList(brand: String? = null, type: String? = null, isFeatured: Boolean = false) {
        val intent = Intent(this, ProductListActivity::class.java).apply {
            brand?.let { putExtra("FILTER_BRAND", it) }
            type?.let { putExtra("FILTER_TYPE", it) }
            if (isFeatured) {
                putExtra("FILTER_FEATURED", true)
            }
        }
        startActivity(intent)
    }
}