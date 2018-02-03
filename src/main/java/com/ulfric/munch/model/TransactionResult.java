package com.ulfric.munch.model;

import java.util.List;

public class TransactionResult {

	private Boolean status;
	private List<Error> errors;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

}
