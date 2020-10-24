package com.alex.comicdiscovery.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.databinding.ActivityMainBinding
import com.alex.comicdiscovery.feature.main.models.UiModelThemes
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModel()

    // ----------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setupViewModel()
    }

    // ----------------------------------------------------------------------------

    private fun setupView() {
        binding.bottomNavigation.also {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            it.setupWithNavController(navHostFragment.navController)
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        }
    }

    private fun setupViewModel() {
        viewModel.themeState.observe(this) { state ->
            when (state) {
                UiModelThemes.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                UiModelThemes.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                UiModelThemes.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            }.also { AppCompatDelegate.setDefaultNightMode(it) }
        }
    }
}