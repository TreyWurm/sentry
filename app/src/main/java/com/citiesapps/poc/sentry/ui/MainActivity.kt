package com.citiesapps.poc.sentry.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.citiesapps.poc.sentry.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.root.addView(Button(this).apply {
            text = "Button 1"
            setOnClickListener { onButton1Clicked() }
        })

        binding.root.addView(Button(this).apply {
            text = "Button 2"
            setOnClickListener { onButton2Clicked() }
        })

        binding.root.addView(Button(this).apply {
            text = "Button 3"
            setOnClickListener { onButton3Clicked() }
        })
    }

    private fun onButton1Clicked() {
        throw RuntimeException("Testing sentry")
    }

    private fun onButton2Clicked() {

    }

    private fun onButton3Clicked() {

    }
}