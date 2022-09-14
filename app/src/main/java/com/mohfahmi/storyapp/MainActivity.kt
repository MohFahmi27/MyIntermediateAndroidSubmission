package com.mohfahmi.storyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mohfahmi.storyapp.core.utils.NavigationHelper.AUTH_ROUTE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNUSED_EXPRESSION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            delay(4500)
            withContext(Dispatchers.Main) {
                navigateToAuth()
            }
        }
    }

    private fun navigateToAuth() {
        startActivity(Intent(this, Class.forName(AUTH_ROUTE)))
        this.finish()
    }
}