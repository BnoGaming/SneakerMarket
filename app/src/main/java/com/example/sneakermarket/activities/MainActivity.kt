package com.example.sneakermarket.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sneakermarket.R

class MainActivity : AppCompatActivity() {

    // Set the duration of the splash screen
    private val SPLASH_DELAY: Long = 2500 // 2.5 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Set the custom layout
        setContentView(R.layout.activity_main)

        // 2. Find the TextView
        val splashText: TextView = findViewById(R.id.splashLogoText)

        // 3. Load and start the animation
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in)
        splashText.startAnimation(fadeIn)

        // Make the text visible *after* starting the animation
        splashText.visibility = TextView.VISIBLE

        // 4. Use a Handler to delay navigation
        Handler(Looper.getMainLooper()).postDelayed({
            // Start the LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            // Finish this activity so the user can't go back to it
            finish()
        }, SPLASH_DELAY)
    }
}