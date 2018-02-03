package com.ulfric.munch.model;

public class Card {

	private String number;
	private String securityCode;
	private Expiration expiration;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public Expiration getExpiration() {
		return expiration;
	}

	public void setExpiration(Expiration expiration) {
		this.expiration = expiration;
	}

}
