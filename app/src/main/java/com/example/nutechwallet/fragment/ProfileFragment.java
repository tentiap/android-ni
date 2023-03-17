package com.example.nutechwallet.fragment;

import android.content.Intent;
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
import com.example.nutechwallet.activity.MainActivity;
import com.example.nutechwallet.api.ApiClient;
import com.example.nutechwallet.api.ApiInterface;
import com.example.nutechwallet.model.getProfile.GetProfileData;
import com.example.nutechwallet.model.getProfile.GetProfileResponse;
import com.example.nutechwallet.model.updateProfile.UpdateProfileData;
import com.example.nutechwallet.model.updateProfile.UpdateProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    SessionManager sessionManager;
    EditText edtFirstName, edtLastName;
    Button btnUpdate, btnLogout, btnCancel, btnSave;
    String firstName, lastName, token, updatedFirstName, updatedLastName;
    ApiInterface apiInterface;
    Boolean isEditing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(getContext());
        token = sessionManager.getUserDetail().get(SessionManager.TOKEN);

        isEditing = false;

        getProfile();

        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);

        btnUpdate = view.findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this);

        edtFirstName = view.findViewById(R.id.edt_firstName_profile);

        edtFirstName.setEnabled(false);

        edtLastName = view.findViewById(R.id.edt_lastName_profile);
        edtLastName.setEnabled(false);

        return view;
    }

    private void getProfile() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetProfileResponse> getProfileResponseCall = apiInterface.getProfile("Bearer " + token);
        getProfileResponseCall.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 0) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        GetProfileData getProfileData = response.body().getData();
                        firstName = getProfileData.getFirstName();
                        lastName = getProfileData.getLastName();

                        setProfileData();

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
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setProfileData() {
        if(firstName != null && lastName != null) {
            edtFirstName.setText(firstName);
            edtFirstName.setEnabled(false);
            edtLastName.setText(lastName);
            edtLastName.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                sessionManager.logoutSession();
                moveToMain();
                break;

            case R.id.btn_update:
                isEditing = true;

                if (isEditing) {
                    edtFirstName.setEnabled(true);
                    edtLastName.setEnabled(true);

                    btnUpdate.setVisibility(View.GONE);
                    btnLogout.setVisibility(View.GONE);
                    btnSave.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnSave.setVisibility(View.GONE);
                            btnCancel.setVisibility(View.GONE);
                            btnUpdate.setVisibility(View.VISIBLE);
                            btnLogout.setVisibility(View.VISIBLE);

                            edtFirstName.setEnabled(false);
                            edtLastName.setEnabled(false);

                            edtFirstName.setText(firstName);
                            edtLastName.setText(lastName);

                            isEditing = false;

                        }
                    });

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isEditing = false;

                            if (isEditing == false) {

                                updatedFirstName = edtFirstName.getText().toString();
                                updatedLastName = edtLastName.getText().toString();

                                updateProfile();

                                btnSave.setVisibility(View.GONE);
                                btnCancel.setVisibility(View.GONE);
                                btnUpdate.setVisibility(View.VISIBLE);
                                btnLogout.setVisibility(View.VISIBLE);

                                edtFirstName.setEnabled(false);
                                edtLastName.setEnabled(false);

                                edtFirstName.setText(firstName);
                                edtLastName.setText(lastName);
                            }
                        }
                    });
                    break;
                }
        }
    }

    private void updateProfile() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UpdateProfileResponse> updateProfileResponseCall = apiInterface.updateProfileResponse("Bearer " + token, updatedFirstName, updatedLastName);
        updateProfileResponseCall.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 0) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        UpdateProfileData updateProfileData = response.body().getData();
                        firstName = updateProfileData.getFirstName();
                        lastName = updateProfileData.getLastName();

                        setProfileData();

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
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void moveToMain() {
        Intent intentProfile = new Intent(getActivity(), MainActivity.class);
        intentProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intentProfile);
    }
}