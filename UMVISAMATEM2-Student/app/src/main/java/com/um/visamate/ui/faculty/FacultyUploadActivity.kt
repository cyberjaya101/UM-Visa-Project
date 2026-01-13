package com.um.visamate.ui.faculty

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.um.visamate.R
import com.um.visamate.data.models.Role
import com.um.visamate.data.models.Submission
import com.um.visamate.data.models.User
import com.um.visamate.utils.FakeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FacultyUploadActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var tvStudentName: TextView
    private lateinit var tvConfirmationStatus: TextView
    private lateinit var btnUploadConfirmation: MaterialButton
    private lateinit var tvResultStatus: TextView
    private lateinit var btnUploadResult: MaterialButton

    private var student: User? = null
    private var submission: Submission? = null
    
    // State variable to track which document is being uploaded
    private var currentDocumentType: String? = null

    // A single launcher, identical to the student's activity
    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.also { uri ->
                // Use the state variable to handle the result
                currentDocumentType?.let { docType ->
                    handleFileUpload(uri, docType)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_upload)

        bindViews()
        setupClickListeners()

        val studentId = intent.getStringExtra("studentId")
        val currentUserId = intent.getStringExtra("currentUserId")

        if (studentId == null || currentUserId == null) {
            showErrorAndFinish("Missing user information.")
            return
        }

        val currentUser = FakeDatabase.findUserById(currentUserId)
        if (currentUser?.role != Role.REVIEWER) {
            showErrorAndFinish("Unauthorized access.")
            return
        }

        student = FakeDatabase.findUserById(studentId)
        if (student == null) {
            showErrorAndFinish("Student not found.")
            return
        }

        tvStudentName.text = "Uploading for: ${student?.name ?: "Unknown Student"}"
        loadSubmission(studentId)
    }

    private fun loadSubmission(studentId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            submission = withContext(Dispatchers.IO) { 
                FakeDatabase.getSubmissionForUser(studentId)
            }
            if (submission == null) {
                submission = withContext(Dispatchers.IO) {
                    FakeDatabase.createSubmission(Submission(userId = studentId))
                }
            }
            updateUI()
        }
    }

    private fun bindViews() {
        btnBack = findViewById(R.id.btnBack)
        tvStudentName = findViewById(R.id.tvStudentName)
        tvConfirmationStatus = findViewById(R.id.tvConfirmationStatus)
        btnUploadConfirmation = findViewById(R.id.btnUploadConfirmation)
        tvResultStatus = findViewById(R.id.tvResultStatus)
        btnUploadResult = findViewById(R.id.btnUploadResult)
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener { finish() }

        btnUploadConfirmation.setOnClickListener {
            // Set state and open picker
            currentDocumentType = "ConfirmationLetter"
            openFilePicker()
        }

        btnUploadResult.setOnClickListener {
            // Set state and open picker
            currentDocumentType = "ResultTranscript"
            openFilePicker()
        }
    }

    /**
     * This function is now identical to the one in DocumentSubmissionActivity.
     */
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Allows any file type to be selected
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            filePickerLauncher.launch(Intent.createChooser(intent, "Select a file to upload"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleFileUpload(uri: Uri, documentType: String) {
        submission?.let { sub ->
            val updatedSubmission = when (documentType) {
                "ConfirmationLetter" -> sub.copy(hasConfirmationLetter = true)
                "ResultTranscript" -> sub.copy(hasResultTranscript = true)
                else -> sub
            }

            CoroutineScope(Dispatchers.Main).launch {
                submission = withContext(Dispatchers.IO) {
                    FakeDatabase.updateSubmission(updatedSubmission)
                }
                Toast.makeText(this@FacultyUploadActivity, "$documentType uploaded", Toast.LENGTH_SHORT).show()
                updateUI()
            }
        }
    }

    private fun updateUI() {
        val sub = submission
        if (sub?.hasConfirmationLetter == true) {
            tvConfirmationStatus.text = "Status: Uploaded"
            btnUploadConfirmation.text = "Replace"
            btnUploadConfirmation.setIconResource(R.drawable.ic_check)
        } else {
            tvConfirmationStatus.text = "Status: Not Uploaded"
            btnUploadConfirmation.text = "Upload"
            btnUploadConfirmation.icon = null
        }

        if (sub?.hasResultTranscript == true) {
            tvResultStatus.text = "Status: Uploaded"
            btnUploadResult.text = "Replace"
            btnUploadResult.setIconResource(R.drawable.ic_check)
        } else {
            tvResultStatus.text = "Status: Not Uploaded"
            btnUploadResult.text = "Upload"
            btnUploadResult.icon = null
        }
    }

    private fun showErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }
}
