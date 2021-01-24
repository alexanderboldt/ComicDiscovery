package com.alex.comicdiscovery.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModel()

    // ----------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupViewModel()
    }

    // ----------------------------------------------------------------------------

    private fun setupViewModel() {
        viewModel.startFragmentState.observe(this) { state ->
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment)
                .navController
                .also { navController ->
                    navController.graph = navController
                        .navInflater
                        .inflate(R.navigation.nav_graph_login)
                        .apply { startDestination = state }
                }
        }
    }
}