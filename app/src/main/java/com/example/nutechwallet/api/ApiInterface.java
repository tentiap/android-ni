package com.example.nutechwallet.api;

import com.example.nutechwallet.model.getBalance.GetBalanceResponse;
import com.example.nutechwallet.model.getProfile.GetProfileResponse;
import com.example.nutechwallet.model.history.HistoryResponse;
import com.example.nutechwallet.model.login.LoginResponse;
import com.example.nutechwallet.model.topup.TopupResponse;
import com.example.nutechwallet.model.transfer.TransferResponse;
import com.example.nutechwallet.model.updateProfile.UpdateProfileResponse;
import com.example.nutechwallet.model.register.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> loginResponse (
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("registration")
    Call<RegisterResponse> registerResponse (
            @Field("email") String email,
            @Field("password") String password,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name
    );

    @FormUrlEncoded
    @POST("updateProfile")
    Call<UpdateProfileResponse> updateProfileResponse(@Header("Authorization") String token,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name
    );

    @GET("getProfile")
    Call<GetProfileResponse> getProfile(@Header("Authorization") String token);

    @GET("transactionHistory")
    Call<HistoryResponse> historyResponse(@Header("Authorization") String token);

    @GET("balance")
    Call<GetBalanceResponse> getBalanceResponse(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("topup")
    Call<TopupResponse> topUpResponse(@Header("Authorization") String token,
                                              @Field("amount") Integer amount
    );

    @FormUrlEncoded
    @POST("transfer")
    Call<TransferResponse> transferResponse(@Header("Authorization") String token,
                                            @Field("amount") Integer amount
    );
}
