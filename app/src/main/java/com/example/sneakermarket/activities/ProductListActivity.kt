package com.example.sneakermarket.activities
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakermarket.R
import com.example.sneakermarket.adapters.ProductGridAdapter
import com.example.sneakermarket.models.Product
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.sneakermarket.utils.FilterBottomSheetFragment

// RENAMED from HomeActivity
class ProductListActivity : AppCompatActivity(), FilterBottomSheetFragment.OnFilterApplyListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductGridAdapter
    private lateinit var filterButton: FloatingActionButton
    private val allProducts = mutableListOf<Product>()
    private val displayedProducts = mutableListOf<Product>()
    // Store current filters
    private var currentBrandFilter: String? = null
    private var currentTypeFilter: String? = null
    private var currentFeaturedFilter: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        intent.extras?.let {
            currentBrandFilter = it.getString("FILTER_BRAND")
            currentTypeFilter = it.getString("FILTER_TYPE")
            currentFeaturedFilter = it.getBoolean("FILTER_FEATURED")
        }

        // Find views
        recyclerView = findViewById(R.id.productsRecyclerView)
        filterButton = findViewById(R.id.filterButton)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        loadProducts()

        productAdapter = ProductGridAdapter(displayedProducts) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                // Existing Data
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

        recyclerView.adapter = productAdapter

        applyFilters(
            brandFilter = currentBrandFilter,
            typeFilter = currentTypeFilter,
            isFeatured = currentFeaturedFilter
        )

        // Set up the advanced filter button
        filterButton.setOnClickListener {
            val filterDialog = FilterBottomSheetFragment()
            filterDialog.show(supportFragmentManager, FilterBottomSheetFragment.TAG)
        }
    }

    // 2. IMPLEMENT the interface method
    override fun onApply(
        minPrice: Double?,
        maxPrice: Double?,
        size: Double?,
        color: String?
    ) {
        applyFilters(
            minPrice = minPrice,
            maxPrice = maxPrice,
            sizeFilter = size,
            colorFilter = color
        )
    }

    private fun applyFilters(
        brandFilter: String? = currentBrandFilter,
        typeFilter: String? = currentTypeFilter,
        isFeatured: Boolean = currentFeaturedFilter,
        sizeFilter: Double? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        colorFilter: String? = null
    ) {
        // Save current base filters
        currentBrandFilter = brandFilter
        currentTypeFilter = typeFilter
        currentFeaturedFilter = isFeatured

        var filteredList = allProducts.toList()

        // Apply base filters
        if (!brandFilter.isNullOrEmpty()) {
            filteredList = filteredList.filter { it.brand.equals(brandFilter, ignoreCase = true) }
        }
        if (!typeFilter.isNullOrEmpty()) {
            filteredList = filteredList.filter { it.type.equals(typeFilter, ignoreCase = true) }
        }
        if (isFeatured) {
            filteredList = filteredList.filter { it.isFeatured }
        }

        // Apply advanced filters
        if (sizeFilter != null) {
            filteredList = filteredList.filter { it.availableSizes.contains(sizeFilter) }
        }
        if (minPrice != null) {
            filteredList = filteredList.filter { it.price >= minPrice }
        }
        if (maxPrice != null) {
            filteredList = filteredList.filter { it.price <= maxPrice }
        }
        if (!colorFilter.isNullOrEmpty()) {
            filteredList = filteredList.filter { it.color.equals(colorFilter, ignoreCase = true) }
        }

        filteredList = filteredList.sortedWith(compareByDescending<Product> { it.isFeatured }.thenByDescending { it.rating })

        displayedProducts.clear()
        displayedProducts.addAll(filteredList)
        productAdapter.notifyDataSetChanged()
    }

    private fun loadProducts() {
        allProducts.clear()
        allProducts.apply {
            val allSizes = listOf(7.0, 7.5, 8.0, 8.5, 9.0, 9.5, 10.0, 10.5, 11.0, 12.0)

            add(Product(1, "Nike Dunk Low 'Civilist'", "Nike", "Skateboarding", 450.0, "https://images.pexels.com/photos/6050923/pexels-photo-6050923.jpeg", allSizes.shuffled().take(5), "Black", 4.8, isFeatured = true))
            add(Product(2, "Yeezy Foam Runner 'Ararat'", "Adidas", "Lifestyle", 320.0, "https://images.pexels.com/photos/13457674/pexels-photo-13457674.jpeg", allSizes.shuffled().take(6), "White", 4.5))
            add(Product(3, "Yeezy Boost 350 'Zebra'", "Adidas", "Lifestyle", 550.0, "https://images.pexels.com/photos/6050909/pexels-photo-6050909.jpeg", allSizes.shuffled().take(7), "White", 4.7, isFeatured = true))
            add(Product(4, "Travis Scott x Fragment Jordan 1", "Jordan", "Basketball", 2800.0, "https://images.pexels.com/photos/7543639/pexels-photo-7543639.jpeg", allSizes.shuffled().take(4), "Blue", 4.9, isFeatured = true))
            add(Product(5, "Air Jordan 1 Mid 'Bred Toe'", "Jordan", "Basketball", 200.0, "https://images.pexels.com/photos/2385477/pexels-photo-2385477.jpeg", allSizes.shuffled().take(8), "Red", 4.4))
            add(Product(6, "New Balance 550 'White Green'", "New Balance", "Lifestyle", 380.0, "https://images.pexels.com/photos/10531564/pexels-photo-10531564.jpeg", allSizes.shuffled().take(5), "Green", 4.6))
            add(Product(7, "Nike Air Force 1 '07", "Nike", "Lifestyle", 110.0, "https://images.pexels.com/photos/1027130/pexels-photo-1027130.jpeg", allSizes, "White", 4.7))
            add(Product(8, "Converse Chuck 70 High", "Converse", "Lifestyle", 85.0, "https://images.pexels.com/photos/1598507/pexels-photo-1598507.jpeg", allSizes, "Black", 4.3))
            add(Product(9, "Adidas Ultraboost 22", "Adidas", "Running", 190.0, "https://images.pexels.com/photos/975006/pexels-photo-975006.jpeg", allSizes.shuffled().take(7), "Black", 4.5))
            add(Product(10, "Air Jordan 4 'Military Black'", "Jordan", "Basketball", 410.0, "https://images.pexels.com/photos/12767786/pexels-photo-12767786.jpeg", allSizes.shuffled().take(6), "White", 4.8, isFeatured = true))
            add(Product(11, "Nike SB Dunk Low 'Pigeon'", "Nike", "Skateboarding", 1100.0, "https://images.pexels.com/photos/11027937/pexels-photo-11027937.jpeg", allSizes.shuffled().take(3), "Gray", 4.9))
            add(Product(12, "Yeezy Slide 'Onyx'", "Adidas", "Lifestyle", 150.0, "https://images.pexels.com/photos/1159670/pexels-photo-1159670.jpeg", allSizes.shuffled().take(8), "Black", 4.2))
            add(Product(13, "New Balance 990v5", "New Balance", "Running", 185.0, "https://images.pexels.com/photos/1598508/pexels-photo-1598508.jpeg", allSizes.shuffled().take(5), "Gray", 4.7))
            add(Product(14, "Air Jordan 1 High 'Mocha'", "Jordan", "Basketball", 550.0, "https://images.pexels.com/photos/5710186/pexels-photo-5710186.jpeg", allSizes.shuffled().take(6), "Brown", 4.8, isFeatured = true))
            add(Product(15, "Nike Air Max 1 'Patta'", "Nike", "Lifestyle", 300.0, "https://images.pexels.com/photos/1304647/pexels-photo-1304647.jpeg", allSizes.shuffled().take(4), "Blue", 4.6))
            add(Product(16, "Puma Suede Classic", "Puma", "Lifestyle", 70.0, "https://images.pexels.com/photos/1159676/pexels-photo-1159676.jpeg", allSizes, "Red", 4.1))
        }
    }
}