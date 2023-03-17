package com.example.nutechwallet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nutechwallet.SessionManager;
import com.example.nutechwallet.databinding.ActivityHomeBinding;
import com.example.nutechwallet.fragment.HistoryFragment;
import com.example.nutechwallet.fragment.HomeFragment;
import com.example.nutechwallet.fragment.ProfileFragment;
import com.example.nutechwallet.R;
import com.example.nutechwallet.fragment.TopupFragment;
import com.example.nutechwallet.fragment.TransferFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityHomeBinding binding;
    SessionManager sessionManager;
    String strToken, strFirstName, strLastName, strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Fragment());

        sessionManager = new SessionManager(HomeActivity.this);
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }

        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.menu_history:
                    replaceFragment(new HistoryFragment());
                    break;

                case R.id.menu_topup:
                    replaceFragment(new TopupFragment());
                    break;

                case R.id.menu_profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });

        binding.fabTransfer.setOnClickListener(this);

        strEmail = sessionManager.getUserDetail().get(SessionManager.EMAIL);
        strFirstName = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
        strLastName = sessionManager.getUserDetail().get(SessionManager.LAST_NAME);
        strToken = sessionManager.getUserDetail().get(SessionManager.TOKEN);

    }

    private void moveToLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

//        HomeFragment homeFragment = new HomeFragment();
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.frame_layout, homeFragment, "HomeFragment")
//                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_transfer:
                replaceFragment(new TransferFragment());
                break;
        }
    }
}