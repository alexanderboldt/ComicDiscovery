package com.alex.comicdiscovery.feature.character.starred

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex.comicdiscovery.databinding.FragmentCharacterStarredBinding
import com.alex.comicdiscovery.feature.base.BaseFragment
import com.alex.comicdiscovery.feature.character.starred.model.RecyclerViewState
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

        binding.customRecyclerView.getRecyclerView().also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
            it.setHasFixedSize(true)
        }
    }

    private fun setupViewModel() {
        viewModel.recyclerViewState.observe { state ->
            when (state) {
                is RecyclerViewState.CharacterState -> {
                    binding.customRecyclerView.showItems()
                    adapter.setCharacters(state.characters)
                }
                is RecyclerViewState.LoadingState -> {
                    binding.customRecyclerView.showLoading(state.message)
                }
                is RecyclerViewState.MessageState -> {
                    binding.customRecyclerView.showMessage(state.message)
                }
            }
        }

        viewModel.detailState.observe { state ->
            CharacterStarredFragmentDirections
                .actionToCharacterDetailFragment(state, false)
                .also { directions ->
                    findNavController().navigate(directions)
                }
        }
    }
}