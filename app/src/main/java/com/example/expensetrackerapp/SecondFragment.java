package com.example.expensetrackerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.expensetrackerapp.data.CompanyContract;
import com.example.expensetrackerapp.data.ReceiptContract;
import com.example.expensetrackerapp.databinding.FragmentSecondBinding;
import com.example.expensetrackerapp.repository.CompanyRepository;
import com.example.expensetrackerapp.repository.ReceiptEntity;
import com.example.expensetrackerapp.repository.ReceiptRepository;

import java.time.LocalDateTime;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ReceiptContract.ReceiptEntry.ReceiptDbHelper dbHelper;
    private ReceiptRepository receiptRepository;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        dbHelper = new ReceiptContract.ReceiptEntry.ReceiptDbHelper(getContext());
        receiptRepository = new ReceiptRepository(dbHelper);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.goPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.addReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                var companyIdEditText = (EditText) view.findViewById(R.id.companyId);
                var invoiceIdEditText = (EditText) view.findViewById(R.id.invoiceId);
                var categoryEditText = (EditText) view.findViewById(R.id.category);
                var amountEditText = (EditText) view.findViewById(R.id.totalAmount);
                var descriptionEditText = (EditText) view.findViewById(R.id.description);

                var companyId = "0";
                var invoiceId = "";
                var category = "";
                var description = "";

                if (companyIdEditText != null) companyId = companyIdEditText.getText().toString();
                if (invoiceIdEditText != null) invoiceId = invoiceIdEditText.getText().toString();
                if (categoryEditText != null) category = categoryEditText.getText().toString();
                if (descriptionEditText != null)
                    description = descriptionEditText.getText().toString();

                System.out.println(companyId + " " + invoiceId + category + " " + description);

                int amountInCents = (int) (Double.parseDouble(amountEditText.getText().toString()) * 100);

                ReceiptEntity receiptEntity = new ReceiptEntity(
                        invoiceId,
                        Integer.parseInt(companyId),
                        amountInCents,
                        category,
                        LocalDateTime.now().toString(),
                        description);

                receiptRepository.insert(receiptEntity);

                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}