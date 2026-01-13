package com.um.visamate.ui.payment

import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.um.visamate.R
import com.um.visamate.data.models.User
import com.um.visamate.utils.FakeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// PaymentActivity: mock payment processor (F.R.3.x, NFR 3.1 mock HTTPS) - Updated for new design
class PaymentActivity : AppCompatActivity() {
    private lateinit var btnProceedPayment: MaterialButton
    private lateinit var radioFpx: RadioButton
    private lateinit var radioCredit: RadioButton
    private lateinit var cbConfirm: MaterialCheckBox
    private lateinit var btnBack: ImageView
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        btnProceedPayment = findViewById(R.id.btnProceedPayment)
        radioFpx = findViewById(R.id.radioFpx)
        radioCredit = findViewById(R.id.radioCredit)
        cbConfirm = findViewById(R.id.cbConfirm)
        btnBack = findViewById(R.id.btnBack)

        val userId = intent.getStringExtra("userId")
        if (userId != null) user = FakeDatabase.findUserById(userId)

        btnBack.setOnClickListener {
            finish()
        }

        radioFpx.setOnClickListener {
            radioCredit.isChecked = false
        }

        radioCredit.setOnClickListener {
            radioFpx.isChecked = false
        }

        btnProceedPayment.setOnClickListener {
            if (!cbConfirm.isChecked) {
                Toast.makeText(this, "Please confirm the checkbox", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                // Simulate network payment processing
                delay(800)
                runOnUiThread {
                    Toast.makeText(this@PaymentActivity, getString(R.string.msg_payment_success), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}

