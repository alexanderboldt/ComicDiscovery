package com.alex.comicdiscovery.feature.character.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alex.comicdiscovery.databinding.FragmentCharacterDetailBinding
import com.alex.comicdiscovery.feature.base.BaseFragment
import com.alex.comicdiscovery.feature.character.detail.models.ContentState
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ldralighieri.corbind.view.clicks

class CharacterDetailFragment : BaseFragment() {

    private val viewModel: CharacterDetailViewModel by viewModel()

    private lateinit var binding: FragmentCharacterDetailBinding

    private val arguments: CharacterDetailFragmentArgs by navArgs()

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)

        setupViewBinding()
        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupViewBinding() {
        lifecycleScope.launch {
            binding.imageViewStar.clicks {
                viewModel.onClickStar()
            }
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

        viewModel.init(arguments.id, arguments.userComesFromStarredScreen)
    }
}