package com.ramilkapev.moviereviews.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramilkapev.moviereviews.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MovieReviews)
        super.onCreate(savedInstanceState)

        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
        finish()
    }
}