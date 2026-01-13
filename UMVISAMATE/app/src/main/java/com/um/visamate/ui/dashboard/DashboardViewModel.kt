package com.um.visamate.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.um.visamate.utils.FakeDatabase
import kotlinx.coroutines.launch

// DashboardViewModel provides quick-loading summaries to meet NFR 2.1
class DashboardViewModel : ViewModel() {
    fun loadUserSubmissions(userId: String, onResult: (Int) -> Unit) {
        viewModelScope.launch {
            val list = FakeDatabase.listSubmissionsForUser(userId)
            onResult(list.size)
        }
    }
}

