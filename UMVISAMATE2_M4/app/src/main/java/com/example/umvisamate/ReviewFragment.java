package com.example.umvisamate;

import android.app.AlertDialog;
// 2. 修复颜色和状态列表报错
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

// 3. 修复 OnBackPressedCallback 报错
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
// 4. 修复 ContextCompat 报错
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

// 5. 修复 MaterialCardView 报错
import com.google.android.material.card.MaterialCardView;

public class ReviewFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 处理手机物理返回键：按下时回到学生列表
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 2. 加载布局文件
        View view = inflater.inflate(R.layout.activity_detail, container, false);

        // 3. 修复返回按钮逻辑 (Toolbar 上的箭头)
        Toolbar toolbar = view.findViewById(R.id.detail_toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> {
                getParentFragmentManager().popBackStack(); // 返回学生列表
            });
        }

        // 4. 初始化卡片并设置跳转提示
        MaterialCardView cardPassport = view.findViewById(R.id.card_passport);
        MaterialCardView cardPhoto = view.findViewById(R.id.card_photo);
        MaterialCardView cardLetter = view.findViewById(R.id.card_letter);

        if (cardPassport != null) {
            cardPassport.setOnClickListener(v -> showNavigationInfo("Passport Document", "Navigating to Passport view..."));
        }

        if (cardPhoto != null) {
            cardPhoto.setOnClickListener(v -> showNavigationInfo("Passport Photo", "Navigating to Photo view..."));
        }

        if (cardLetter != null) {
            cardLetter.setOnClickListener(v -> showNavigationInfo("Enrollment Letter", "Navigating to Letter view..."));
        }

        // 5. 批准与驳回按钮逻辑
        // 在 ReviewFragment.java 的 onCreateView 中
        View btnApprove = view.findViewById(R.id.btnApprove);
        View btnReject = view.findViewById(R.id.btnReject);

        if (btnApprove != null) {
            // 使用 Steel Blue (#4A7FBA)
            int color = ContextCompat.getColor(requireContext(), R.color.interactive_blue);
            btnApprove.setBackgroundTintList(ColorStateList.valueOf(color));
        }

        if (btnReject != null) {
            // 使用 Light Gray (#F3F4F5)
            int color = ContextCompat.getColor(requireContext(), R.color.bg_light_gray);
            btnReject.setBackgroundTintList(ColorStateList.valueOf(color));
        }

        return view;
    }

    // 弹窗提示函数
    private void showNavigationInfo(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}