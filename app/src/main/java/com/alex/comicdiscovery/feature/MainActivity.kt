package com.alex.comicdiscovery.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // ----------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
    }

    // ----------------------------------------------------------------------------

    private fun setupView() {
        binding.bottomNavigation.also {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            it.setupWithNavController(navHostFragment.navController)
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        }
    }
}