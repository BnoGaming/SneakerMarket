package com.example.sneakermarket.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sneakermarket.R

class CheckoutActivity : AppCompatActivity() {
    private lateinit var productNameText: TextView
    private lateinit var totalPriceText: TextView
    private lateinit var addressInput: EditText
    private lateinit var cardInput: EditText
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        productNameText = findViewById(R.id.checkoutProductName)
        totalPriceText = findViewById(R.id.totalPrice)
        addressInput = findViewById(R.id.addressInput)
        cardInput = findViewById(R.id.cardInput)
        confirmButton = findViewById(R.id.confirmButton)

        val name = intent.getStringExtra("PRODUCT_NAME")
        val price = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)

        productNameText.text = name
        totalPriceText.text = "Total: $$price"

        confirmButton.setOnClickListener {
            val address = addressInput.text.toString()
            val card = cardInput.text.toString()

            if (address.isNotEmpty() && card.isNotEmpty()) {
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}