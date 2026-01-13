package com.example.umvisamate_m4;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * This Fragment replaces the old DashboardPaidActivity.
 * It shows the "Paid" status and enables the final submission button.
 */
public class DashboardPaidFragment extends Fragment {

    private Button btnSubmit;

    // Logic from your original Activity
    private boolean isChecklistComplete = true;
    private boolean isPaymentComplete = true;

    public DashboardPaidFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Use renamed layout: fragment_dashboard_paid.xml
        return inflater.inflate(R.layout.fragment_dashboard_paid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Initialize the submit button
        btnSubmit = view.findViewById(R.id.btn_submit_visa);

        // 2. Apply your color and enablement logic
        if (isChecklistComplete && isPaymentComplete) {
            // Activate button: Dark Blue
            btnSubmit.setEnabled(true);
            btnSubmit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#163269")));
        } else {
            // Deactivate button: Light Blue/Gray
            btnSubmit.setEnabled(false);
            btnSubmit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B0C4DE")));
        }

        // 3. Final submission feedback
        btnSubmit.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Application Submitted to UM Visa Unit!", Toast.LENGTH_LONG).show();
        });

        // 4. Back button logic
        View ivBack = view.findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        }
    }
}