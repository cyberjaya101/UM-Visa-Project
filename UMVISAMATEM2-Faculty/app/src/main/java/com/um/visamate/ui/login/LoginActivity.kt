package com.um.visamate.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton
import com.um.visamate.R
import com.um.visamate.data.models.Role
import com.um.visamate.data.models.User
import com.um.visamate.ui.dashboard.DashboardActivity
import com.um.visamate.ui.faculty.FacultyPortalActivity
import com.um.visamate.ui.officer.OfficerDashboardActivity
import com.um.visamate.utils.FakeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var cardRoleStudent: MaterialCardView
    private lateinit var cardRoleFaculty: MaterialCardView
    private lateinit var cardRoleVisa: MaterialCardView
    private lateinit var cbRemember: MaterialCheckBox
    private lateinit var btnLogin: MaterialButton
    private lateinit var tvForgot: TextView

    private var selectedRole: Role = Role.APPLICANT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FakeDatabase.initialize(applicationContext)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        cardRoleStudent = findViewById(R.id.cardRoleStudent)
        cardRoleFaculty = findViewById(R.id.cardRoleFaculty)
        cardRoleVisa = findViewById(R.id.cardRoleVisa)
        cbRemember = findViewById(R.id.cbRemember)
        btnLogin = findViewById(R.id.btnLogin)
        tvForgot = findViewById(R.id.tvForgot)

        listOf(cardRoleStudent, cardRoleFaculty, cardRoleVisa).forEach { card ->
            card.isCheckable = true
            card.setOnClickListener { onRoleCardClicked(card) }
        }
        setSelectedCard(cardRoleStudent)

        tvForgot.setOnClickListener { }

        btnLogin.setOnClickListener {
            val emailText = etEmail.text?.toString()?.trim().orEmpty()
            val passwordText = etPassword.text?.toString().orEmpty()

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                etEmail.error = if (emailText.isEmpty()) getString(R.string.label_email_or_id) else null
                etPassword.error = if (passwordText.isEmpty()) getString(R.string.label_password) else null
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val name = emailText
                val existing = FakeDatabase.findUserByName(name)
                val user = existing ?: FakeDatabase.addUser(User(name = name, role = selectedRole))

                runOnUiThread {
                    val intent = when (user.role) {
                        Role.APPLICANT -> Intent(this@LoginActivity, DashboardActivity::class.java)
                        Role.REVIEWER -> Intent(this@LoginActivity, FacultyPortalActivity::class.java)
                        Role.ADMIN -> Intent(this@LoginActivity, OfficerDashboardActivity::class.java)
                    }
                    intent.putExtra("userId", user.id)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun onRoleCardClicked(card: MaterialCardView) {
        setSelectedCard(card)
    }

    private fun setSelectedCard(card: MaterialCardView) {
        cardRoleStudent.isChecked = card == cardRoleStudent
        cardRoleFaculty.isChecked = card == cardRoleFaculty
        cardRoleVisa.isChecked = card == cardRoleVisa

        selectedRole = when (card) {
            cardRoleStudent -> Role.APPLICANT
            cardRoleFaculty -> Role.REVIEWER
            else -> Role.ADMIN
        }
    }
}
