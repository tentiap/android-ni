package com.example.nutechwallet.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutechwallet.R;
import com.example.nutechwallet.SessionManager;
import com.example.nutechwallet.api.ApiClient;
import com.example.nutechwallet.api.ApiInterface;
import com.example.nutechwallet.model.topup.TopupResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopupFragment extends Fragment implements View.OnClickListener {

    SessionManager sessionManager;
    EditText edtAmount;
    Button btnTopup;
    String amountString, token;
    Integer amount;
    ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topup, container, false);

        sessionManager = new SessionManager(getContext());
        token = sessionManager.getUserDetail().get(SessionManager.TOKEN);

        btnTopup = view.findViewById(R.id.btn_topup);
        btnTopup.setOnClickListener(this);

        edtAmount = view.findViewById(R.id.edt_amount_topup);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_topup:
                topup();
                break;
        }
    }

    private void topup() {
        amountString = edtAmount.getText().toString();

        if (amountString.isEmpty()) {
            edtAmount.setError("Silakan input jumlah");
        } else {
            amount = Integer.parseInt(amountString);

            if (amount < 999) {
                edtAmount.setError("Minimal topup adalah Rp1.000");
            } else {

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<TopupResponse> topupResponseCall = apiInterface.topUpResponse("Bearer " + token, amount);
                topupResponseCall.enqueue(new Callback<TopupResponse>() {
                    @Override
                    public void onResponse(Call<TopupResponse> call, Response<TopupResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 0) {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                edtAmount.setText("");

                            } else if (response.body().getStatus() == 108) {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), "Unexpected status: "+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TopupResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}