package com.alex.comicdiscovery.feature.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.alex.comicdiscovery.databinding.FragmentSettingsBinding
import com.alex.comicdiscovery.feature.base.BaseFragment
import com.alex.comicdiscovery.feature.settings.models.UiModelThemes
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment() {

    private val viewModel: SettingsViewModel by viewModel()

    private lateinit var binding: FragmentSettingsBinding

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        setupViewBinding()
        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupViewBinding() {
        lifecycleScope.launch {
            binding.textViewThemeSystem.setOnClickListener { viewModel.onSelectTheme(UiModelThemes.SYSTEM) }
            binding.textViewThemeLight.setOnClickListener { viewModel.onSelectTheme(UiModelThemes.LIGHT) }
            binding.textViewThemeDark.setOnClickListener { viewModel.onSelectTheme(UiModelThemes.DARK) }
        }
    }

    private fun setupViewModel() {
        viewModel.themeState.observe { state ->
            when (state) {
                UiModelThemes.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                UiModelThemes.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                UiModelThemes.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            }.also { AppCompatDelegate.setDefaultNightMode(it) }
        }
    }
}