package com.example.sneakermarket.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.sneakermarket.R
// --- ADD THESE IMPORTS ---
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var productImage: ImageView
    private lateinit var favoriteIcon: ImageView
    private lateinit var productName: TextView
    private lateinit var productPrice: TextView
    private lateinit var productDescription: TextView
    private lateinit var buyButton: Button
    private lateinit var productBrand: TextView
    private lateinit var productType: TextView

    // --- ADD THESE NEW VIEWS ---
    private lateinit var productColor: TextView
    private lateinit var sizeChipGroup: ChipGroup

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Find all existing views
        productImage = findViewById(R.id.productImage)
        favoriteIcon = findViewById(R.id.favoriteIcon)
        productName = findViewById(R.id.productName)
        productPrice = findViewById(R.id.productPrice)
        productDescription = findViewById(R.id.productDescription)
        buyButton = findViewById(R.id.buyButton)
        productBrand = findViewById(R.id.productBrand)
        productType = findViewById(R.id.productType)

        // --- FIND NEW VIEWS ---
        productColor = findViewById(R.id.productColor)
        sizeChipGroup = findViewById(R.id.sizeChipGroup)

        // Get existing data from Intent
        val name = intent.getStringExtra("PRODUCT_NAME")
        val price = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
        val imageUrl = intent.getStringExtra("PRODUCT_IMAGE")
        val brand = intent.getStringExtra("PRODUCT_BRAND")
        val type = intent.getStringExtra("PRODUCT_TYPE")

        // --- GET NEW DATA FROM INTENT ---
        val color = intent.getStringExtra("PRODUCT_COLOR")
        // Get the ArrayList and cast it, be careful with the type.
        val sizes = intent.getSerializableExtra("PRODUCT_SIZES") as? ArrayList<Double>

        // Set data to views
        productName.text = name
        productPrice.text = "$$price"
        productBrand.text = brand
        productType.text = type
        productColor.text = color // Set the color

        // You can create a more dynamic description now
        productDescription.text = "Discover the $name, a premium $type sneaker from $brand. This $color model is a must-have for any collector."

        Glide.with(this)
            .load(imageUrl)
            .into(productImage)

        // --- POPULATE DYNAMIC SIZES ---
        populateSizeChips(sizes)

        favoriteIcon.setOnClickListener {
            isFavorite = !isFavorite
            val newAlpha = if (isFavorite) 1.0f else 0.5f
            it.animate().alpha(newAlpha).setDuration(200).start()
        }

        buyButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("PRODUCT_NAME", name)
            intent.putExtra("PRODUCT_PRICE", price)
            startActivity(intent)
        }
    }

    /**
     * Dynamically creates and adds size chips to the ChipGroup.
     */
    private fun populateSizeChips(sizes: List<Double>?) {
        if (sizes.isNullOrEmpty()) {
            // Optionally show a "No sizes available" text
            return
        }

        for (size in sizes) {
            val chip = Chip(this)
            chip.text = size.toString()
            chip.isCheckable = true
            chip.isClickable = true
            // Apply a style to your chip if you have one in themes.xml
            // chip.setStyle(R.style.Widget_App_Chip_Choice)

            sizeChipGroup.addView(chip)
        }
    }
}