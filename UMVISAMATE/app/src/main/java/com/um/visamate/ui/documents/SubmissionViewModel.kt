package com.um.visamate.ui.documents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.um.visamate.data.models.Submission
import com.um.visamate.utils.FakeDatabase
import com.um.visamate.utils.MockNetworkClient
import com.um.visamate.utils.RoleManager
import kotlinx.coroutines.launch

// SubmissionViewModel enforces FR and NFR constraints (Final Submission Lock, RBAC)
class SubmissionViewModel : ViewModel() {
    fun submit(userId: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val dummy = Submission(userId = userId)
                RoleManager.requireSubmit(FakeDatabase.findUserById(userId))
                FakeDatabase.createSubmission(dummy)
                val ok = MockNetworkClient.uploadSubmission(dummy, minDurationMs = 500L)
                if (ok) {
                    dummy.status = com.um.visamate.data.models.SubmissionStatus.SUBMITTED
                    dummy.submittedAt = System.currentTimeMillis()
                    FakeDatabase.updateSubmission(dummy)
                    onResult(true, null)
                } else onResult(false, "Upload failed")
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }
}

