package com.um.visamate.ui.documents

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
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
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_submission)

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
            return
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnUploadPassport.setOnClickListener {
            Toast.makeText(this, "Upload passport photo", Toast.LENGTH_SHORT).show()
            // Mock upload
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

        btnRequestFaculty.setOnClickListener {
            Toast.makeText(this, "Faculty documents requested", Toast.LENGTH_SHORT).show()
            // Mock faculty document request
        }
    }
}
