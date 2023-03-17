package com.example.nutechwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutechwallet.R;
import com.example.nutechwallet.api.ApiClient;
import com.example.nutechwallet.api.ApiInterface;
import com.example.nutechwallet.model.register.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword, edtFirstName, edtLastName;
    Button btnRegister;
    String email, password, firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edt_email_register);
        edtPassword = findViewById(R.id.edt_password_register);
        edtFirstName = findViewById(R.id.edt_first_name_register);
        edtLastName = findViewById(R.id.edt_last_name_register);

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                firstName = edtFirstName.getText().toString();
                lastName = edtLastName.getText().toString();

                if (email.trim().equals("")) {
                    edtEmail.setError("Email wajib diiisi");
                } else if (password.trim().equals("")) {
                    edtPassword.setError("Password wajib diisi");
                } else if (firstName.trim().equals("")) {
                    edtFirstName.setError("First Name wajib diisi");
                } else if (lastName.trim().equals("")) {
                    edtLastName.setError("Last Name wajib diisi");
                } else {
                    register();
                }
            }
        });
    }

    private void register() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RegisterResponse> call = apiInterface.registerResponse(email, password, firstName, lastName);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 0) {
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        Intent intentRegister = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intentRegister);
                        finish();
                    } else if (response.body().getStatus() == 103) {
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Unexpected status: "+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}