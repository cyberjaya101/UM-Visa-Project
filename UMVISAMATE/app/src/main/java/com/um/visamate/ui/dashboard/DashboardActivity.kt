package com.um.visamate.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.um.visamate.R
import com.um.visamate.data.models.User
import com.um.visamate.ui.documents.DocumentSubmissionActivity
import com.um.visamate.ui.payment.PaymentActivity
import com.um.visamate.utils.FakeDatabase
import com.um.visamate.utils.RoleManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// DashboardActivity (F.R.1.x, NFR 2.1) - Updated for new design
class DashboardActivity : AppCompatActivity() {
    private lateinit var btnContinueRenewal: MaterialButton
    private lateinit var tvWelcome: TextView
    private lateinit var tvStudentId: TextView
    private lateinit var btnMenu: ImageView
    private lateinit var btnNotifications: ImageView
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnContinueRenewal = findViewById(R.id.btnContinueRenewal)
        tvWelcome = findViewById(R.id.tvWelcome)
        tvStudentId = findViewById(R.id.tvStudentId)
        btnMenu = findViewById(R.id.btnMenu)
        btnNotifications = findViewById(R.id.btnNotifications)

        val userId = intent.getStringExtra("userId")
        if (userId != null) {
            user = FakeDatabase.findUserById(userId)
            user?.let {
                tvWelcome.text = getString(R.string.welcome_back, it.name)
                tvStudentId.text = getString(R.string.student_id, userId)
            }
        }

        // RBAC: hide continue button for non-applicants
        if (!RoleManager.canSubmit(user)) {
            btnContinueRenewal.isEnabled = false
            btnContinueRenewal.alpha = 0.5f
        }

        btnContinueRenewal.setOnClickListener {
            val intent = Intent(this, DocumentSubmissionActivity::class.java)
            intent.putExtra("userId", user?.id)
            startActivity(intent)
        }

        btnMenu.setOnClickListener {
            // Open navigation drawer or menu
        }

        btnNotifications.setOnClickListener {
            // Open notifications
        }

        // Load dashboard data quickly to meet NFR 2.1
        CoroutineScope(Dispatchers.Default).launch {
            val submissions = user?.id?.let { FakeDatabase.listSubmissionsForUser(it) } ?: emptyList()
            // Data loaded for status display
        }
    }
}

