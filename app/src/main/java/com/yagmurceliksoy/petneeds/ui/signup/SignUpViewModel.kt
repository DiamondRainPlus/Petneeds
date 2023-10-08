package com.yagmurceliksoy.petneeds.ui.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yagmurceliksoy.petneeds.common.Resource
import com.yagmurceliksoy.petneeds.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _state = MutableLiveData<SignUpState>()
    val state: LiveData<SignUpState>
        get() = _state

     fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignUpState.Loading
            when (val result = userRepository.signUp(email, password)) {
                is Resource.Success -> {
                    _state.value = if (result.data) {
                        SignUpState.GoToHome
                    } else {
                        SignUpState.Error("Something went wrong")
                    }
                }

                is Resource.Error -> {
                    _state.value = SignUpState.Error(result.toString())
                }

                is Resource.Fail -> {

                }
            }
        }
    }

    fun checkInfo(email: String, password: String) {
        when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                _state.value = SignUpState.Error("Invalid mail")
            }

            password.isEmpty() || password.length <= 6 -> {
                _state.value = SignUpState.Error("Invalid password")
            }

            else -> signUp(email, password)
        }
    }
}

sealed interface SignUpState {
    object Loading : SignUpState
    object GoToHome : SignUpState
    data class Error(val s: String) : SignUpState
}