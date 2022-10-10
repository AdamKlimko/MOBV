package com.example.mobv

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener { onButtonPressed() }
    }

    private fun onButtonPressed() {
        val animation = findViewById<LottieAnimationView>(R.id.animationView)
        animation.visibility = View.VISIBLE
    }
}