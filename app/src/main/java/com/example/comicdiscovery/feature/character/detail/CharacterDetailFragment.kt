package com.example.comicdiscovery.feature.character.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.request.RequestOptions
import com.example.comicdiscovery.databinding.FragmentCharacterDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : Fragment() {

    private val viewModel: CharacterDetailViewModel by viewModel()

    private lateinit var binding: FragmentCharacterDetailBinding

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)

//        setupView()
//        setupViewBinding()
        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupViewModel() {
        viewModel.characterState.observe(viewLifecycleOwner, Observer { state ->
            binding.apply {
                glideImageView.load(state.imageUrl, RequestOptions.centerCropTransform())
                textViewName.text = state.name
                textViewRealName.text = state.realName
                textViewAliases.text = state.aliases
                textViewGender.text = state.gender
                textViewBirth.text = state.birth
                textViewPowers.text = state.powers
                textViewOrigin.text = state.origin
            }
        })

        viewModel.init(requireArguments().getInt("id"))
    }
}