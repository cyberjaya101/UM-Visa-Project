package com.example.umvisamate_m4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment; // Use the Fragment class from AndroidX
import androidx.navigation.Navigation;

/**
 * This Fragment replaces the old DashboardPendingActivity.
 * It is managed by MainActivity and the Navigation Graph.
 */
public class DashboardPendingFragment extends Fragment {

    // Default constructor
    public DashboardPendingFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // We renamed 'activity_pending_dashboard' to 'fragment_dashboard_pending' earlier
        return inflater.inflate(R.layout.fragment_dashboard_pending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Initialize the "Make Payment" button
        // In Fragments, we must use 'view.findViewById' to locate components
        Button btnMakePayment = view.findViewById(R.id.btn_make_payment);

        if (btnMakePayment != null) {
            btnMakePayment.setOnClickListener(v -> {
                // Use Jetpack Navigation to move to the Payment screen
                // 'R.id.action_pending_to_payment' will be defined in your nav_graph.xml
                Navigation.findNavController(v).navigate(R.id.action_pending_to_payment);
            });
        }

        // 2. Initialize the Back button
        View btnBack = view.findViewById(R.id.iv_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                // Navigate back or close the current screen
                if (getActivity() != null) {
                    getActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            });
        }
    }
}