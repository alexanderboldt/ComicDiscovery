package com.example.comicdiscovery.feature.character.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.request.RequestOptions
import com.example.comicdiscovery.databinding.FragmentCharacterDetailBinding
import com.example.comicdiscovery.feature.character.detail.models.ContentState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : Fragment() {

    private val viewModel: CharacterDetailViewModel by viewModel()

    private lateinit var binding: FragmentCharacterDetailBinding

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)

        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupViewModel() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { state ->
            binding.contentLoadingProgressBar.apply { if (state) show() else hide() }
        })

        viewModel.contentState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ContentState.CharacterState -> {
                    binding.apply {
                        groupContent.isVisible = true
                        textViewMessage.isGone = true

                        glideImageView.load(state.character.imageUrl, RequestOptions.centerCropTransform())
                        textViewName.text = state.character.name
                        textViewRealName.text = state.character.realName
                        textViewAliases.text = state.character.aliases
                        textViewGender.text = state.character.gender
                        textViewBirth.text = state.character.birth
                        textViewPowers.text = state.character.powers
                        textViewOrigin.text = state.character.origin
                    }
                }
                is ContentState.MessageState -> {
                    binding.apply {
                        groupContent.isGone = true
                        textViewMessage.isVisible = true
                        textViewMessage.text = state.message
                    }
                }
            }
        })

        viewModel.init(requireArguments().getInt("id"))
    }
}