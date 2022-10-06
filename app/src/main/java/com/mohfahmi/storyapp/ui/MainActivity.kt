package com.mohfahmi.storyapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mohfahmi.storyapp.R
import com.mohfahmi.storyapp.core.utils.NavigationHelper.AUTH_ROUTE
import com.mohfahmi.storyapp.core.utils.NavigationHelper.HOME_ROUTE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("UNUSED_EXPRESSION")
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            delay(4500)
            withContext(Dispatchers.Main) {
                viewModel.readLoginState().observe(this@MainActivity) { state ->
                    if (state) navigateToHome() else navigateToAuth()
                }
            }
        }
    }

    private fun navigateToAuth() {
        startActivity(Intent(this, Class.forName(AUTH_ROUTE)))
        this.finish()
    }

    private fun navigateToHome() {
        startActivity(Intent(this, Class.forName(HOME_ROUTE)))
        this.finish()
    }
}