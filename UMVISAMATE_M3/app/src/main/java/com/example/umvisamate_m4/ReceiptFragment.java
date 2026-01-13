package com.example.umvisamate_m4;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class ReceiptFragment extends Fragment {

    public ReceiptFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Use renamed layout: fragment_receipt.xml
        return inflater.inflate(R.layout.fragment_receipt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Play the successful payment animation (the green checkmark)
        ImageView ivCheck = view.findViewById(R.id.iv_check_anim);
        if (ivCheck != null) {
            Drawable drawable = ivCheck.getDrawable();
            if (drawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) drawable).start();
            }
        }

        // 2. "Back to Dashboard" button logic
        Button btnBackToDashboard = view.findViewById(R.id.btn_back_to_dashboard);
        if (btnBackToDashboard != null) {
            btnBackToDashboard.setOnClickListener(v -> {
                // Navigate to the final "Paid" Dashboard
                // This ID will be red until we update the nav_graph
                Navigation.findNavController(v).navigate(R.id.action_receipt_to_paid);
            });
        }
    }
}