package com.example.umvisamate;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 1. 修复 detail_toolbar 报错
        MaterialToolbar toolbar = findViewById(R.id.detail_toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        // 2. 修复卡片变量报错 (ID 必须与 XML 一致)
        MaterialCardView cardPassport = findViewById(R.id.card_passport);
        MaterialCardView cardPhoto = findViewById(R.id.card_photo);
        MaterialCardView cardLetter = findViewById(R.id.card_letter);

        // 点击逻辑
        if (cardPassport != null) {
            cardPassport.setOnClickListener(v -> Toast.makeText(this, "Viewing Passport...", Toast.LENGTH_SHORT).show());
        }

        // 3. 修复按钮变量报错
        Button btnApprove = findViewById(R.id.btnApprove);
        Button btnReject = findViewById(R.id.btnReject);

        if (btnApprove != null) {
            btnApprove.setOnClickListener(v -> {
                Toast.makeText(this, "Application Approved!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        if (btnReject != null) {
            btnReject.setOnClickListener(v -> finish());
        }
    }
}