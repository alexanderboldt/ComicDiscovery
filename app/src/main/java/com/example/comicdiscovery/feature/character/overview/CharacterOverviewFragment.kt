package com.example.comicdiscovery.feature.character.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comicdiscovery.R
import com.example.comicdiscovery.databinding.FragmentCharacterOverviewBinding
import com.example.comicdiscovery.feature.base.BaseFragment
import com.example.comicdiscovery.feature.character.overview.models.RecyclerViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterOverviewFragment : BaseFragment() {

    private val viewModel: CharacterOverviewViewModel by viewModel()

    private lateinit var binding: FragmentCharacterOverviewBinding

    private lateinit var adapter: CharacterOverviewAdapter

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
        adapter = CharacterOverviewAdapter { id ->
            viewModel.onClickCharacter(id)
        }

        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
            it.setHasFixedSize(true)
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
        viewModel.loadingState.observe { state ->
            binding.contentLoadingProgressBar.apply { if (state) show() else hide() }
        }

        viewModel.recyclerViewState.observe { state ->
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
        }

        viewModel.detailState.observe { state ->
            findNavController().navigate(R.id.action_characterOverviewFragment_to_characterDetailFragment, bundleOf("id" to state))
        }
    }
}