package com.alex.comicdiscovery.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.databinding.FragmentHomeBinding
import com.alex.comicdiscovery.feature.base.BaseFragment
import com.alex.comicdiscovery.feature.home.models.UiModelThemes
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupView()
        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupView() {
        binding.bottomNavigation.also {
            val navHostFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            it.setupWithNavController(navHostFragment.navController)
            it.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        }
    }

    private fun setupViewModel() {
        viewModel.themeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                UiModelThemes.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                UiModelThemes.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                UiModelThemes.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            }.also { AppCompatDelegate.setDefaultNightMode(it) }
        }
    }
}