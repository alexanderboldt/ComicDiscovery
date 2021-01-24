package com.alex.comicdiscovery.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alex.comicdiscovery.databinding.FragmentLoginBinding
import com.alex.comicdiscovery.feature.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ldralighieri.corbind.view.clicks

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModel()

    private lateinit var binding: FragmentLoginBinding

    // ----------------------------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupView()
        setupViewBinding()
        setupViewModel()

        return binding.root
    }

    // ----------------------------------------------------------------------------

    private fun setupView() {
    }

    private fun setupViewBinding() {
        lifecycleScope.launch {
            binding.buttonLogin.clicks {
                viewModel.onClickLogin(binding.editTextUsername.text.toString(), binding.editTextPassword.text.toString())
            }
        }
    }

    private fun setupViewModel() {
        viewModel.mainScreenState.observe {
            LoginFragmentDirections
                .actionToHomeFragment()
                .also { findNavController().navigate(it) }
        }

        viewModel.toastState.observe { state ->
            Toast.makeText(requireContext(), state, Toast.LENGTH_LONG).show()
        }
    }
}