package com.example.expensetrackerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.expensetrackerapp.data.CompanyContract;
import com.example.expensetrackerapp.repository.CompanyEntity;
import com.example.expensetrackerapp.repository.CompanyRepository;

import java.time.LocalDateTime;

public class AddCompanyDialogFragment extends DialogFragment {

    private CompanyContract.CompanyEntry.CompanyDbHelper dbHelper;
    private CompanyRepository companyRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_add_company_dialog, container, false);
        dbHelper = new CompanyContract.CompanyEntry.CompanyDbHelper(getContext());
        companyRepository = new CompanyRepository(dbHelper);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        var button = (Button) view.findViewById(R.id.addCompanyButton);
        button.setOnClickListener(v -> {
            var companyName = (EditText) view.findViewById(R.id.companyName);
            var companyAddress = (EditText) view.findViewById(R.id.companyAddress);
            var companyDesc = (EditText) view.findViewById(R.id.companyDescription);
            var outletId = (EditText) view.findViewById(R.id.outletId);

            CompanyEntity companyEntity = new CompanyEntity(
                    companyName.getText().toString(),
                    companyAddress.getText().toString(),
                    companyDesc.getText().toString(),
                    outletId.getText().toString(),
                    LocalDateTime.now().toString());
            companyRepository.insert(companyEntity);
            dismiss();
        });
    }
}
