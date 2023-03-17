package com.example.nutechwallet.model.getBalance;

import com.google.gson.annotations.SerializedName;

public class GetBalanceData {

	@SerializedName("balance")
	private int balance;

	public void setBalance(int balance){
		this.balance = balance;
	}

	public int getBalance(){
		return balance;
	}
}