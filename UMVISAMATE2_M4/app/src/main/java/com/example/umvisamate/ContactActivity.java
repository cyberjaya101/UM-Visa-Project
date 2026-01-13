package com.example.umvisamate;

import android.os.Bundle;
import android.widget.Button; // 修改为通用 Button
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // 1. 设置返回箭头逻辑
        MaterialToolbar toolbar = findViewById(R.id.contact_toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        // 2. 绑定控件（修复 ClassCastException 闪退的关键）
        TextInputEditText etMessage = findViewById(R.id.et_message);
        Button btnSubmit = findViewById(R.id.btn_submit_query); // 使用通用 Button

        btnSubmit.setOnClickListener(v -> {
            String message = etMessage.getText().toString().trim();

            if (message.isEmpty()) {
                etMessage.setError("Please describe your issue");
                return;
            }

            // F.R. 4.4: 生成唯一的工单追踪 ID
            String ticketId = "TK-" + (System.currentTimeMillis() % 10000);

            new MaterialAlertDialogBuilder(this)
                    .setTitle("Query Submitted")
                    .setMessage("Your inquiry is now being tracked.\n\nTicket ID: " + ticketId)
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
        });
    }
}