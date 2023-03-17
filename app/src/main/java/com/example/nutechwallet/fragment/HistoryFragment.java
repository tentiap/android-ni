package com.example.nutechwallet.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutechwallet.R;
import com.example.nutechwallet.SessionManager;
import com.example.nutechwallet.adapter.HistoryAdapter;
import com.example.nutechwallet.api.ApiClient;
import com.example.nutechwallet.api.ApiInterface;
import com.example.nutechwallet.model.history.HistoryData;
import com.example.nutechwallet.model.history.HistoryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {
    private RecyclerView rvHistory;
    private List<HistoryData> historyDataList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    SessionManager sessionManager;
    ImageView imgviewStart;
    TextView textViewCall, textViewHistory;
    String token;
    ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        rvHistory = view.findViewById(R.id.rv_history);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        progressBar = view.findViewById(R.id.progress_bar);

        sessionManager = new SessionManager(getContext());
        token = sessionManager.getUserDetail().get(SessionManager.TOKEN);

        imgviewStart = view.findViewById(R.id.imgview_start_history);
        textViewCall = view.findViewById(R.id.tv_callToAction_history);
        textViewHistory = view.findViewById(R.id.tv_history);

        showHistoryList();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                showHistoryList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void showHistoryList() {
        progressBar.setVisibility(View.VISIBLE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<HistoryResponse> historyResponseCall = apiInterface.historyResponse("Bearer " + token);
        historyResponseCall.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 0) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
                        historyDataList = response.body().getData();

                        final HistoryAdapter historyAdapter = new HistoryAdapter(getContext(), historyDataList);
                        rvHistory.setAdapter(historyAdapter);
                        historyAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);

                        historyAdapter.setOnItemClickCallback(new HistoryAdapter.OnItemClickCallback() {
                            @Override
                            public void onItemClicked(HistoryData data) {
                                Toast.makeText(getContext(), "Transaksi " +response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else if (response.body().getStatus() == 108) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showEmptyMessage();
                        progressBar.setVisibility(View.INVISIBLE);

                    } else {
                        Toast.makeText(getContext(), "Unexpected status: "+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        showEmptyMessage();
                    }

                } else {
                    Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    showEmptyMessage();
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                showEmptyMessage();
            }
        });
    }

    private void showEmptyMessage() {
        textViewHistory.setVisibility(View.INVISIBLE);
        imgviewStart.setVisibility(View.VISIBLE);
        textViewCall.setVisibility(View.VISIBLE);
    }
}