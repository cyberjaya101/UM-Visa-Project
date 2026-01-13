package com.um.visamate.ui.officer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.um.visamate.R

// OfficerDashboardActivity (RBAC-protected officer view)
class OfficerDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_officer_dashboard)
    }
}

