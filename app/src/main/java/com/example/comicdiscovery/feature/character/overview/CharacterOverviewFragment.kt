package com.example.comicdiscovery.feature.character.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comicdiscovery.databinding.FragmentCharacterOverviewBinding
import com.example.comicdiscovery.feature.character.overview.models.RecyclerViewState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterOverviewFragment : Fragment() {

    private val viewModel: CharacterOverviewViewModel by viewModel()

    private lateinit var binding: FragmentCharacterOverviewBinding

    private val adapter by lazy { CharacterOverviewAdapter() }

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterOverviewBinding.inflate(inflater, container, false)

        setupView()
        setupViewBinding()
        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupView() {
        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
        binding.contentLoadingProgressBar.show()
    }

    private fun setupViewBinding() {
        binding.searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSubmitSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
    }

    private fun setupViewModel() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { state ->
            binding.contentLoadingProgressBar.apply { if (state) show() else hide() }
        })

        viewModel.recyclerViewState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is RecyclerViewState.CharacterState -> {
                    binding.apply {
                        recyclerView.isVisible = true
                        textViewMessage.isGone = true
                    }
                    adapter.setCharacters(state.characters)
                }
                is RecyclerViewState.MessageState -> {
                    binding.apply {
                        recyclerView.isInvisible = true
                        textViewMessage.isVisible = true
                        textViewMessage.text = state.message
                    }
                }
            }
        })
    }
}