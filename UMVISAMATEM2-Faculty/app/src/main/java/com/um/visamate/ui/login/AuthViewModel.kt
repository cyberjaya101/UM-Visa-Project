package com.um.visamate.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.um.visamate.data.models.Role
import com.um.visamate.data.models.User
import com.um.visamate.utils.FakeDatabase
import kotlinx.coroutines.launch

// AuthViewModel handles login logic (F.R.1.1 - F.R.1.4, NFR 1.3 multilingual foundation)
class AuthViewModel : ViewModel() {
    fun login(name: String, role: Role, onResult: (User) -> Unit) {
        viewModelScope.launch {
            val existing = FakeDatabase.findUserByName(name)
            val user = existing ?: FakeDatabase.addUser(User(name = name, role = role))
            onResult(user)
        }
    }
}

