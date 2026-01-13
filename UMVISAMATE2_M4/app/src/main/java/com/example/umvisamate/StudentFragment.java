package com.example.umvisamate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.umvisamate.R;

public class StudentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        // 统一的跳转逻辑
        View.OnClickListener goToReview = v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ReviewFragment())
                    .addToBackStack(null) // 允许返回到学生列表
                    .commit();
        };

        // 绑定 4 位学生卡片
        view.findViewById(R.id.student_1).setOnClickListener(goToReview);
        view.findViewById(R.id.student_2).setOnClickListener(goToReview);
        view.findViewById(R.id.student_3).setOnClickListener(goToReview);
        view.findViewById(R.id.student_4).setOnClickListener(goToReview);

        return view;
    }
}