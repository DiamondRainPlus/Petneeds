package com.yagmurceliksoy.petneeds.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yagmurceliksoy.petneeds.R
import com.yagmurceliksoy.petneeds.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

        with(binding) {
            signupButton.setOnClickListener {
                val email = signupEmail.text.toString()
                val password = signupPassword.text.toString()

                    viewModel.checkInfo(email, password)
            }

            loginRedirectText.setOnClickListener {
                findNavController().navigate(R.id.signUptoSingIn)
            }
        }

        observeData()

    }


    private fun observeData() = with(binding) {

        viewModel.state.observe(viewLifecycleOwner) { state ->

            when (state) {
                is SignUpState.GoToHome -> {
                    findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                }

                is SignUpState.Error -> {
                    Snackbar.make(requireView(), "Error", 1000).show()
                }

                is SignUpState.Loading -> {
                    Snackbar.make(requireView(), "Loading", 1000).show()
                }
            }
        }
    }
}