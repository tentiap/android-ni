package com.example.nutechwallet.model.updateProfile;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileData {

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("first_name")
	private String firstName;

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}
}