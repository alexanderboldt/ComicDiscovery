package com.alex.comicdiscovery.feature.character.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.alex.comicdiscovery.databinding.FragmentCharacterDetailBinding
import com.alex.comicdiscovery.feature.base.BaseFragment
import com.alex.comicdiscovery.feature.character.detail.models.ContentState
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : BaseFragment() {

    private val viewModel: CharacterDetailViewModel by viewModel()

    private lateinit var binding: FragmentCharacterDetailBinding

    // ----------------------------------------------------------------------------

    companion object {
        private const val KEY_ID = "KEY_ID"
        private const val KEY_SCREEN = "KEY_SCREEN"

        fun bundle(id: Int, userComesFromStarredScreen: Boolean)
                = bundleOf(KEY_ID to id, KEY_SCREEN to userComesFromStarredScreen)
    }

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)

        setupViewBinding()
        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupViewBinding() {
        binding.imageViewStar.setOnClickListener {
            viewModel.onClickStar()
        }
    }

    private fun setupViewModel() {
        viewModel.loadingState.observe { state ->
            binding.contentLoadingProgressBar.apply { if (state) show() else hide() }
        }

        viewModel.contentState.observe { state ->
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
        }

        viewModel.starState.observe { state ->
            binding.imageViewStar.setImageResource(state)
        }

        requireArguments().apply { viewModel.init(getInt(KEY_ID), getBoolean(KEY_SCREEN)) }
    }
}