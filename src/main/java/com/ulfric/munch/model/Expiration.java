package com.ulfric.munch.model;

import java.time.Month;
import java.time.Year;

public class Expiration {

	private Month month;
	private Year year;

	public Month getMonth() {
		return month;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

}
