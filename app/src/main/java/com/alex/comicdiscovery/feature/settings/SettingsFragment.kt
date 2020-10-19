package com.alex.comicdiscovery.feature.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.databinding.FragmentSettingsBinding
import com.alex.comicdiscovery.feature.base.BaseFragment
import com.alex.comicdiscovery.feature.settings.models.UiModelThemes
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ldralighieri.corbind.widget.itemSelections

class SettingsFragment : BaseFragment() {

    private val viewModel: SettingsViewModel by viewModel()

    private lateinit var binding: FragmentSettingsBinding

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        setupView()
        setupViewBinding()
        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupView() {
        ArrayAdapter
            .createFromResource(requireContext(), R.array.settings_theme, android.R.layout.simple_spinner_dropdown_item)
            .also { binding.spinner.adapter = it }
    }

    private fun setupViewBinding() {
        lifecycleScope.launch {
            binding.spinner.itemSelections { position ->
                viewModel.onSelectTheme(UiModelThemes.values()[position])
            }
        }
    }

    private fun setupViewModel() {
        viewModel.themeState.observe { state ->
            binding.spinner.setSelection(state.ordinal)

            when (state) {
                UiModelThemes.SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                UiModelThemes.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                UiModelThemes.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}