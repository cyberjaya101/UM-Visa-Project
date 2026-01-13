package com.um.visamate.ui.faculty

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.um.visamate.R
import com.um.visamate.data.models.Role
import com.um.visamate.utils.FakeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FacultyPortalActivity : AppCompatActivity() {

    // Buttons
    private lateinit var btnUploadDocs1: MaterialButton
    private lateinit var btnUploadDocs2: MaterialButton

    // Student info
    private lateinit var tvStudentName1: TextView
    private lateinit var tvStudentDetails1: TextView
    private lateinit var tvStudentName2: TextView
    private lateinit var tvStudentDetails2: TextView

    // Counts
    private lateinit var tvPendingCount: TextView
    private lateinit var tvCompletedCount: TextView

    // Cards
    private lateinit var cardUrgentRequest: MaterialCardView
    private lateinit var cardNormalRequest: MaterialCardView

    // Urgent UI
    private lateinit var urgentLabel: TextView

    // Activity result launcher
    private lateinit var uploadLauncher: ActivityResultLauncher<Intent>

    // Hardcoded student IDs
    private val ahmedId = "user-ahmed-khan"
    private val sarahId = "user-sarah-lee"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_portal)

        // ===== Bind views =====
        btnUploadDocs1 = findViewById(R.id.btnUploadDocs1)
        btnUploadDocs2 = findViewById(R.id.btnUploadDocs2)

        tvStudentName1 = findViewById(R.id.tvStudentName1)
        tvStudentDetails1 = findViewById(R.id.tvStudentDetails1)
        tvStudentName2 = findViewById(R.id.tvStudentName2)
        tvStudentDetails2 = findViewById(R.id.tvStudentDetails2)

        tvPendingCount = findViewById(R.id.tvPendingCount)
        tvCompletedCount = findViewById(R.id.tvCompletedCount)

        cardUrgentRequest = findViewById(R.id.cardUrgentRequest)
        cardNormalRequest = findViewById(R.id.cardNormalRequest)

        urgentLabel = findViewById(R.id.tvUrgentLabel)

        // ===== Validate login =====
        val currentUserId = intent.getStringExtra("userId")
        if (currentUserId.isNullOrEmpty()) {
            Toast.makeText(this, "Login required.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val currentUser = FakeDatabase.findUserById(currentUserId)
        if (currentUser == null || currentUser.role != Role.REVIEWER) {
            Toast.makeText(this, "Unauthorized access.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // ===== Populate UI =====
        populateStudentInfo()
        populateCounts()

        // ===== Register activity result launcher =====
        uploadLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                // Refresh UI for both students on return
                refreshStudentUI(ahmedId)
                refreshStudentUI(sarahId)
            }

        // ===== Button actions =====
        btnUploadDocs1.setOnClickListener {
            openUploadScreen(ahmedId, currentUserId)
        }

        btnUploadDocs2.setOnClickListener {
            openUploadScreen(sarahId, currentUserId)
        }
    }

    private fun openUploadScreen(studentId: String, currentUserId: String) {
        val intent = Intent(this, FacultyUploadActivity::class.java).apply {
            putExtra("studentId", studentId)
            putExtra("currentUserId", currentUserId)
        }
        uploadLauncher.launch(intent)
    }

    private fun populateStudentInfo() {
        val ahmed = FakeDatabase.findUserById(ahmedId)
        if (ahmed != null) {
            tvStudentName1.text = ahmed.name
            tvStudentDetails1.text = "${ahmed.studentNumber} • Visa details"
        }
        val sarah = FakeDatabase.findUserById(sarahId)
        if (sarah != null) {
            tvStudentName2.text = sarah.name
            tvStudentDetails2.text = "${sarah.studentNumber} • Visa details"
        }
    }

    private fun populateCounts() {
        tvPendingCount.text = "5"
        tvCompletedCount.text = "12"
    }

    private fun refreshStudentUI(studentId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val submission = withContext(Dispatchers.IO) {
                FakeDatabase.getSubmissionForUser(studentId)
            }

            val documentsComplete = submission != null &&
                    submission.hasConfirmationLetter &&
                    submission.hasResultTranscript

            when (studentId) {
                ahmedId -> {
                    if (documentsComplete) {
                        urgentLabel.visibility = View.GONE
                        cardUrgentRequest.strokeWidth = 1
                        cardUrgentRequest.strokeColor = ContextCompat.getColor(this@FacultyPortalActivity, R.color.grey_border)
                    }
                }
                sarahId -> {
                    // In the future, you could add logic here for Sarah's card
                }
            }
        }
    }
}
