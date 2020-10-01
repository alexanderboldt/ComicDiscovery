package com.alex.comicdiscovery.feature.character.starred

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.databinding.FragmentCharacterStarredBinding
import com.alex.comicdiscovery.feature.base.BaseFragment
import com.alex.comicdiscovery.feature.character.detail.CharacterDetailFragment
import com.alex.comicdiscovery.feature.character.starred.models.RecyclerViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterStarredFragment : BaseFragment() {

    private val viewModel: CharacterStarredViewModel by viewModel()

    private lateinit var binding: FragmentCharacterStarredBinding

    private lateinit var adapter: CharacterStarredAdapter

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterStarredBinding.inflate(inflater, container, false)

        setupView()
        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupView() {
        adapter = CharacterStarredAdapter(viewModel::onClickCharacter)

        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
            it.setHasFixedSize(true)
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
            findNavController().navigate(
                R.id.action_characterStarredFragment_to_characterDetailFragment,
                CharacterDetailFragment.bundle(state, false))
        }
    }
}