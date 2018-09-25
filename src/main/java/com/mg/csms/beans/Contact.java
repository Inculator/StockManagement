package com.mg.csms.beans;

/**
 * @author Mohak Gupta
 *
 */
public class Contact {

	private String address;
	private final Long phoneNo = 985854554L;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
	}

}
