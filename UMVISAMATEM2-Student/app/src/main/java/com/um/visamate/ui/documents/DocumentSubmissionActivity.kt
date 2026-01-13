package com.um.visamate.ui.documents

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.um.visamate.R
import com.um.visamate.data.models.Submission
import com.um.visamate.data.models.SubmissionStatus
import com.um.visamate.data.models.User
import com.um.visamate.utils.FakeDatabase
import com.um.visamate.utils.MockNetworkClient
import com.um.visamate.utils.RoleManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// DocumentSubmissionActivity implements F.R.2.x and enforces Final Submission Lock - Updated for new design
class DocumentSubmissionActivity : AppCompatActivity() {
    private lateinit var btnUploadPassport: MaterialButton
    private lateinit var btnReplacePassport: MaterialButton
    private lateinit var btnUploadFinancial: MaterialButton
    private lateinit var btnRequestFaculty: MaterialButton
    private lateinit var btnBack: ImageView

    private var facultyDocumentsRequested = false

    private var user: User? = null

    // ActivityResultLauncher for F.R. 2.3
    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val selectedFileUri: Uri? = data?.data
            if (selectedFileUri != null) {
                // Handle the selected file URI (e.g., display file name, upload)
                Toast.makeText(this, "File selected: ${selectedFileUri.path}", Toast.LENGTH_LONG).show()
                // Here you would typically start the upload process for the selected file
            } else {
                Toast.makeText(this, getString(R.string.msg_no_file_selected), Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_submission)

        // Initialize views
        btnUploadPassport = findViewById(R.id.btnUploadPassport)
        btnReplacePassport = findViewById(R.id.btnReplacePassport)
        btnUploadFinancial = findViewById(R.id.btnUploadFinancial)
        btnRequestFaculty = findViewById(R.id.btnRequestFaculty)
        btnBack = findViewById(R.id.btnBack)


        val userId = intent.getStringExtra("userId")
        if (userId != null) user = FakeDatabase.findUserById(userId)

        // RBAC: only applicants can submit
        if (!RoleManager.canSubmit(user)) {
            btnUploadPassport.isEnabled = false
            btnUploadFinancial.isEnabled = false
            btnRequestFaculty.isEnabled = false
            Toast.makeText(this, getString(R.string.msg_final_locked), Toast.LENGTH_LONG).show()
            updateUiForChecklist() // F.R. 2.2
            return
        }

        setupClickListeners()
        updateUiForChecklist() // F.R. 2.2
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        // F.R. 2.3: Student File Upload
        btnUploadPassport.setOnClickListener {
            openFilePicker()
        }

        btnReplacePassport.setOnClickListener {
            Toast.makeText(this, "Replace scanned passport", Toast.LENGTH_SHORT).show()
            // Mock replace
        }

        btnUploadFinancial.setOnClickListener {
            // Create a draft submission and upload via MockNetworkClient
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val submission = Submission(userId = user!!.id)
                    FakeDatabase.createSubmission(submission)
                    val success = MockNetworkClient.uploadSubmission(submission, minDurationMs = 500L)
                    if (success) {
                        submission.status = SubmissionStatus.SUBMITTED
                        submission.submittedAt = System.currentTimeMillis()
                        FakeDatabase.updateSubmission(submission)
                        runOnUiThread {
                            Toast.makeText(this@DocumentSubmissionActivity, getString(R.string.msg_upload_success), Toast.LENGTH_SHORT).show()
                            updateUiForChecklist() // F.R. 2.2
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@DocumentSubmissionActivity, getString(R.string.msg_upload_failure), Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread { Toast.makeText(this@DocumentSubmissionActivity, e.message, Toast.LENGTH_SHORT).show() }
                }
            }
        }

        // F.R. 2.1: Faculty Document Request
        btnRequestFaculty.setOnClickListener {
            // Mock faculty document request
            facultyDocumentsRequested = true
            updateUiForChecklist() // Update UI to reflect the request
            Toast.makeText(this, "Faculty documents requested", Toast.LENGTH_SHORT).show()
            // In a real app, this would trigger a backend call to notify the faculty officer.
        }
    }

    /**
     * F.R. 2.3: Opens the device's file picker to select any type of file.
     */
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Allows any file type to be selected
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            filePickerLauncher.launch(Intent.createChooser(intent, "Select a file to upload"))
        } catch (_: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * F.R. 2.2: Updates the UI based on the current document status.
     * This function serves as a placeholder for a more dynamic checklist implementation.
     */
    private fun updateUiForChecklist() {
        // Update faculty document request status
        if (facultyDocumentsRequested) {
            btnRequestFaculty.isEnabled = false
        } else {
            btnRequestFaculty.isEnabled = RoleManager.canSubmit(user)
        }

        // Here, you would add more logic to check the status of each uploaded document
        // from your data source (e.g., FakeDatabase) and update the UI accordingly.
        // For example, showing a green checkmark or changing button text.
    }
}
