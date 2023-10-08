package com.yagmurceliksoy.petneeds.ui.signin

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
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _state = MutableLiveData<SignInState>()
    val state: LiveData<SignInState>
        get() = _state

    private fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignInState.Loading
            when (val result = userRepository.signIn(email, password)) {
                is Resource.Success -> {
                    _state.value = if (result.data) {
                        SignInState.GoToHome
                    } else {
                        SignInState.Error("Something went wrong")
                    }
                }

                is Resource.Error -> {
                    _state.value = SignInState.Error(result.toString())
                }

                is Resource.Fail -> {

                }
            }
        }
    }

    fun checkInfo
                (email: String, password: String) {
        when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                _state.value = SignInState.Error("Invalid Mail")
            }

            password.isEmpty() || password.length <= 6 -> {
                _state.value = SignInState.Error("Invalid password")
            }

            else -> signIn(email, password)
        }
    }

}


sealed interface SignInState {
    object Loading : SignInState
    object GoToHome : SignInState
    data class Error(val throwable: String) : SignInState
}