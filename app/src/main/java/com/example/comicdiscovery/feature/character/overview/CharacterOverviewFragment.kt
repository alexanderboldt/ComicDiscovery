package com.example.comicdiscovery.feature.character.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comicdiscovery.databinding.FragmentCharacterOverviewBinding
import org.koin.android.ext.android.inject

class CharacterOverviewFragment : Fragment() {

    private val viewModel: CharacterOverviewViewModel by inject()

    private lateinit var binding: FragmentCharacterOverviewBinding

    private val adapter by lazy { CharacterOverviewAdapter() }

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterOverviewBinding.inflate(inflater, container, false)

        setupView()
        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupView() {
        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun setupViewModel() {
        viewModel.characters.observe(viewLifecycleOwner, Observer { characters ->
            if (characters == null) return@Observer
            adapter.setCharacters(characters)
        })
    }
}