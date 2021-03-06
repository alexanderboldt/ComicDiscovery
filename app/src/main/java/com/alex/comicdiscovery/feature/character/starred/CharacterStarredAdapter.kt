package com.alex.comicdiscovery.feature.character.starred

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alex.comicdiscovery.databinding.ItemCharacterOverviewBinding
import com.alex.comicdiscovery.feature.character.starred.model.UiModelCharacter

class CharacterStarredAdapter(val characterClick: (Int) -> Unit) : RecyclerView.Adapter<CharacterStarredAdapter.ViewHolder>() {

    private val characters = mutableListOf<UiModelCharacter>()

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
            imageViewIcon.load(character.iconUrl)
        }
    }

    // ----------------------------------------------------------------------------

    fun setCharacters(characters: List<UiModelCharacter>) {
        this.characters.apply {
            clear()
            addAll(characters)
        }
        notifyDataSetChanged()
    }
}