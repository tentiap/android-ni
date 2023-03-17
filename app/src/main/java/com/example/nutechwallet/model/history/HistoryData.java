package com.example.nutechwallet.model.history;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class HistoryData implements Parcelable {

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("amount")
	private int amount;

	@SerializedName("transaction_time")
	private String transactionTime;

	@SerializedName("transaction_type")
	private String transactionType;

	protected HistoryData(Parcel in) {
		transactionId = in.readString();
		amount = in.readInt();
		transactionTime = in.readString();
		transactionType = in.readString();
	}

	public static final Creator<HistoryData> CREATOR = new Creator<HistoryData>() {
		@Override
		public HistoryData createFromParcel(Parcel in) {
			return new HistoryData(in);
		}

		@Override
		public HistoryData[] newArray(int size) {
			return new HistoryData[size];
		}
	};

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setTransactionTime(String transactionTime){
		this.transactionTime = transactionTime;
	}

	public String getTransactionTime(){
		return transactionTime;
	}

	public void setTransactionType(String transactionType){
		this.transactionType = transactionType;
	}

	public String getTransactionType(){
		return transactionType;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(transactionId);
		parcel.writeString(transactionTime);
		parcel.writeString(transactionType);
		parcel.writeInt(amount);
	}
}