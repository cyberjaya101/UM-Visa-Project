package com.example.umvisamate_m4; // Ensure this matches your package name

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class PaymentFragment extends Fragment {

    private Button btnProceed;
    private ProgressBar paymentLoader;
    private RadioButton radioOnline, radioCard;
    private TextView tvVisaFee, tvProcessingFee, tvInsuranceFee, tvTotalFee;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Use the renamed layout file: fragment_payment.xml
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Bind UI components (Note the "view." prefix)
        btnProceed = view.findViewById(R.id.btn_proceed);
        paymentLoader = view.findViewById(R.id.payment_loader);
        radioOnline = view.findViewById(R.id.radio_online_banking);
        radioCard = view.findViewById(R.id.radio_credit_card);

        tvVisaFee = view.findViewById(R.id.tv_visa_fee);
        tvProcessingFee = view.findViewById(R.id.tv_processing_fee);
        tvInsuranceFee = view.findViewById(R.id.tv_insurance_fee);
        tvTotalFee = view.findViewById(R.id.tv_total_fee);

        // 2. Apply the fee calculation for Yemen
        applyNationalityFees("Yemen");

        // 3. RadioButton logic (Mutual Exclusivity)
        radioOnline.setOnClickListener(v -> {
            radioOnline.setChecked(true);
            radioCard.setChecked(false);
            enableProceedButton();
        });

        radioCard.setOnClickListener(v -> {
            radioCard.setChecked(true);
            radioOnline.setChecked(false);
            enableProceedButton();
        });

        // 4. Back button logic
        view.findViewById(R.id.btn_back).setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );

        // 5. Proceed button logic with 3-second simulation
        btnProceed.setOnClickListener(v -> startPaymentSimulation(v));
    }

    private void applyNationalityFees(String nationality) {
        double visa = 90.0, processing = 50.0, insurance = 250.0;
        double total = visa + processing + insurance;

        tvVisaFee.setText("RM " + String.format("%.2f", visa));
        tvProcessingFee.setText("RM " + String.format("%.2f", processing));
        tvInsuranceFee.setText("RM " + String.format("%.2f", insurance));
        tvTotalFee.setText("RM " + String.format("%.2f", total));
    }

    private void enableProceedButton() {
        btnProceed.setEnabled(true);
        // Change color to Dark Blue (your theme color)
        btnProceed.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#163269")));
    }

    private void startPaymentSimulation(View v) {
        // Show loading state
        btnProceed.setEnabled(false);
        btnProceed.setText("Processing...");
        paymentLoader.setVisibility(View.VISIBLE);

        // Wait for 3 seconds, then navigate
        new Handler().postDelayed(() -> {
            // Save payment status to storage
            requireActivity().getSharedPreferences("UM_VISA_PREFS", Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean("is_paid", true)
                    .apply();

            // Navigate to Receipt (This ID will be red for now)
            Navigation.findNavController(v).navigate(R.id.action_payment_to_receipt);
        }, 3000);
    }
}