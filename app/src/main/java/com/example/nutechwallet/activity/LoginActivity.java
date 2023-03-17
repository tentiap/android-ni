package com.example.nutechwallet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutechwallet.R;
import com.example.nutechwallet.SessionManager;
import com.example.nutechwallet.api.ApiClient;
import com.example.nutechwallet.api.ApiInterface;
import com.example.nutechwallet.model.login.LoginData;
import com.example.nutechwallet.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    
    EditText edtEmail, edtPassword;
    Button btnLogin;
    String Email, Password;
    ApiInterface apiInterface;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        edtEmail = findViewById(R.id.edt_email_login);
        edtPassword = findViewById(R.id.edt_password_login);
        
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Email = edtEmail.getText().toString();
                Password = edtPassword.getText().toString();

                if (Email.trim().equals("")) {
                    edtEmail.setError("Email wajib diiisi");
                } else if (Password.trim().equals("")) {
                    edtPassword.setError("Password wajib diisi");
                } else {
                    login(Email, Password);
                }

                break;
        }
    }

    private void login(String email, String password) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> loginResponseCall = apiInterface.loginResponse(email, password);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    sessionManager = new SessionManager(LoginActivity.this);
                    LoginData loginData = response.body().getData();
                    sessionManager.createLoginSession(loginData);

                    Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intentLogin);
                    finish();

                } else {
                    // respons seharusnya "username atau password anda salah", tapi response.body().getMessage() error
                    Toast.makeText(LoginActivity.this, "Salah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}