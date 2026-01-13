package com.um.visamate.ui.faculty

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.um.visamate.R
import com.um.visamate.data.models.Role
import com.um.visamate.utils.FakeDatabase

class FacultyPortalActivity : AppCompatActivity() {

    private lateinit var btnUploadDocs1: MaterialButton
    private lateinit var btnUploadDocs2: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_portal)

        btnUploadDocs1 = findViewById(R.id.btnUploadDocs1)
        btnUploadDocs2 = findViewById(R.id.btnUploadDocs2)

        val currentUserId = intent.getStringExtra("userId")

        // Ensure the user is a faculty member
        val currentUser = FakeDatabase.findUserById(currentUserId ?: "")
        if (currentUser == null || currentUser.role != Role.REVIEWER) {
            Toast.makeText(this, "Unauthorized access.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Hardcoded student IDs for the mock UI
        val ahmedKhanId = "user-ahmed-khan"
        val sarahLeeId = "user-sarah-lee"

        btnUploadDocs1.setOnClickListener {
            val intent = Intent(this, FacultyUploadActivity::class.java).apply {
                putExtra("studentId", ahmedKhanId)
                putExtra("currentUserId", currentUserId)
            }
            startActivity(intent)
        }

        btnUploadDocs2.setOnClickListener {
            val intent = Intent(this, FacultyUploadActivity::class.java).apply {
                putExtra("studentId", sarahLeeId)
                putExtra("currentUserId", currentUserId)
            }
            startActivity(intent)
        }
    }
}
