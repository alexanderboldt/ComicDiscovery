package com.example.comicdiscovery.feature.character.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.comicdiscovery.databinding.ItemCharacterOverviewBinding
import com.example.comicdiscovery.feature.character.overview.models.Character

class CharacterOverviewAdapter(val characterClick: (Int) -> Unit) : RecyclerView.Adapter<CharacterOverviewAdapter.ViewHolder>() {

    private val characters = mutableListOf<Character>()

    // ----------------------------------------------------------------------------

    class ViewHolder(val binding: ItemCharacterOverviewBinding) : RecyclerView.ViewHolder(binding.root)

    // ----------------------------------------------------------------------------

    override fun getItemCount() = characters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCharacterOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val character = characters[position]

        viewHolder.binding.root.setOnClickListener {
            characterClick(character.id)
        }

        viewHolder.binding.apply {
            textViewName.text = character.name
            textViewRealName.isGone = character.realName == null
            textViewRealName.text = character.realName
            imageViewIcon.load(character.iconUrl, RequestOptions.centerCropTransform())
        }
    }

    // ----------------------------------------------------------------------------

    fun setCharacters(characters: List<Character>) {
        this.characters.apply {
            clear()
            addAll(characters)
        }
        notifyDataSetChanged()
    }
}