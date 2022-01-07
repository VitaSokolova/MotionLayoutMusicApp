
package com.example.motionmusicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

/**
 * Mark Activity with launcher intent-filter at AndroidManifest.xml to run this sample
 */
class MusicAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_app)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    }
}
