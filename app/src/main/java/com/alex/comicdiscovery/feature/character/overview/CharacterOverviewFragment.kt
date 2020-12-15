package com.alex.comicdiscovery.feature.character.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex.comicdiscovery.databinding.FragmentCharacterOverviewBinding
import com.alex.comicdiscovery.feature.base.BaseFragment
import com.alex.comicdiscovery.feature.character.overview.models.RecyclerViewState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ldralighieri.corbind.appcompat.queryTextChangeEvents

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
        adapter = CharacterOverviewAdapter(viewModel::onClickCharacter)

        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
            it.setHasFixedSize(true)
        }
    }

    private fun setupViewBinding() {
        lifecycleScope.launch {
            binding.searchView.queryTextChangeEvents { event ->
                if (event.isSubmitted) viewModel.onSubmitSearch(event.queryText.toString())
            }
        }
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
            CharacterOverviewFragmentDirections
                .actionToCharacterDetailFragment(state, false)
                .also { directions ->
                    findNavController().navigate(directions)
                }
        }

        viewModel.hideKeyboardState.observe {
            hideKeyboard()
        }
    }
}