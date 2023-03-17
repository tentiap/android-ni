package com.example.nutechwallet.model.updateProfile;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileResponse {

	@SerializedName("data")
	private UpdateProfileData updateProfileData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(UpdateProfileData updateProfileData){
		this.updateProfileData = updateProfileData;
	}

	public UpdateProfileData getData(){
		return updateProfileData;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}