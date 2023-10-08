package com.yagmurceliksoy.petneeds.ui.signin

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
import com.yagmurceliksoy.petneeds.common.viewBinding
import com.yagmurceliksoy.petneeds.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            loginButton.setOnClickListener {
                val email = loginEmail.text.toString()
                val password = loginPassword.text.toString()

                viewModel.checkInfo(email, password)
            }

            signupRegisterText.setOnClickListener {
                findNavController().navigate(R.id.singIntoSignUp)
            }
        }

        initObservers()
    }

    private fun initObservers() = with(binding) {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {

                SignInState.GoToHome -> findNavController().navigate(R.id.action_signInFragment_to_homeFragment)

                SignInState.Loading -> {
                    Snackbar.make(requireView(), "Loading", 1000).show()
                }


                is SignInState.Error -> {
                    Snackbar.make(requireView(), "Error", 1000).show()
                    }

                }
            }
        }
    }



