package com.alex.comicdiscovery.feature.character.detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel

@Composable
fun CharacterDetailScreen(id: Int) { //, viewModel: CharacterDetailViewModel = getViewModel()) {
    Text(text = "detaiiiiiiiiiiiiiiiiiiiiiiiill with the id $id")
    /*
    private val viewModel: CharacterDetailViewModel by viewModel()
        //parametersOf(arguments.id, arguments.userComesFromStarredScreen)


    private lateinit var binding: FragmentCharacterDetailBinding

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
        viewModel.contentState.observe { state ->
            when (state) {
                is ContentState.CharacterState -> {
                    binding.apply {
                        viewSwitcher.displayedChild = 0

                        imageViewAvatar.load(state.character.imageUrl) {
                            crossfade(500)
                        }
                        textViewName.text = state.character.name
                        textViewRealName.text = state.character.realName
                        textViewAliases.text = state.character.aliases
                        textViewGender.text = state.character.gender
                        textViewBirth.text = state.character.birth
                        textViewPowers.text = state.character.powers
                        textViewOrigin.text = state.character.origin
                    }
                }
                is ContentState.LoadingState -> {
                    binding.apply {
                        viewSwitcher.displayedChild = 1
                        contentLoadingProgressBar.isVisible = true
                        contentLoadingProgressBar.show()
                        textViewMessage.text = state.message
                    }
                }
                is ContentState.MessageState -> {
                    binding.apply {
                        viewSwitcher.displayedChild = 1
                        contentLoadingProgressBar.isGone = true
                        contentLoadingProgressBar.hide()
                        textViewMessage.text = state.message
                    }
                }
            }
        }

        viewModel.starState.observe { state ->
            binding.imageViewStar.setImageResource(state)
        }
    }
     */
}