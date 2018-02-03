package com.ulfric.munch.model;

import java.util.Currency;

public class MonetaryAmount {

	private Long dollars;
	private Long cents;
	private Currency currency;

	public Long getDollars() {
		return dollars;
	}

	public void setDollars(Long dollars) {
		this.dollars = dollars;
	}

	public Long getCents() {
		return cents;
	}

	public void setCents(Long cents) {
		this.cents = cents;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
