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
    private var currentDocumentType: String? = null

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.also { uri ->
                currentDocumentType?.let { handleFileUpload(uri, it) }
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
            Toast.makeText(this, "Missing user information.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val currentUser = FakeDatabase.findUserById(currentUserId)
        if (currentUser?.role != com.um.visamate.data.models.Role.REVIEWER) {
            Toast.makeText(this, "Unauthorized access.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        student = FakeDatabase.findUserById(studentId)
        if (student == null) {
            Toast.makeText(this, "Student not found.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        tvStudentName.text = "Uploading for: ${student!!.name}"
        loadSubmission(studentId)
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
            currentDocumentType = "ConfirmationLetter"
            openFilePicker()
        }

        btnUploadResult.setOnClickListener {
            currentDocumentType = "ResultTranscript"
            openFilePicker()
        }
    }

    private fun loadSubmission(studentId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            submission = withContext(Dispatchers.IO) {
                FakeDatabase.getSubmissionForUser(studentId)
            } ?: withContext(Dispatchers.IO) {
                FakeDatabase.createSubmission(Submission(userId = studentId))
            }
            updateUI()
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        try {
            filePickerLauncher.launch(Intent.createChooser(intent, "Select a file"))
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

                // SEND RESULT BACK TO CALLER
                val resultIntent = Intent().apply {
                    putExtra("studentId", student?.id)
                }
                setResult(Activity.RESULT_OK, resultIntent)

                // close the upload screen so portal can refresh immediately
                finish()
            }
        }
    }

    private fun updateUI() {
        val sub = submission
        if (sub?.hasConfirmationLetter == true) {
            tvConfirmationStatus.text = "Status: Uploaded"
            btnUploadConfirmation.text = "Replace"
        } else {
            tvConfirmationStatus.text = "Status: Not Uploaded"
            btnUploadConfirmation.text = "Upload"
        }

        if (sub?.hasResultTranscript == true) {
            tvResultStatus.text = "Status: Uploaded"
            btnUploadResult.text = "Replace"
        } else {
            tvResultStatus.text = "Status: Not Uploaded"
            btnUploadResult.text = "Upload"
        }
    }
}
