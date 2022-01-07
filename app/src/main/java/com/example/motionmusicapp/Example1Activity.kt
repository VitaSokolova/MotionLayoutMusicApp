package com.example.motionmusicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Sample with moving view from the left to the right
 *
 * Mark Activity with launcher intent-filter at AndroidManifest.xml to run this sample
 */
class Example1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_1)
    }
}
