package com.ulfric.munch;

import java.math.BigDecimal;
import java.util.Objects;

import com.ulfric.munch.model.Transaction;
import com.ulfric.munch.model.TransactionResult;

public abstract class SkeletalGateway implements Gateway {

	protected final Credentials credentials;

	public SkeletalGateway(Credentials credentials) {
		Objects.requireNonNull(credentials, "credentials");

		this.credentials = credentials;
	}

	@Override
	public TransactionResult process(Transaction transaction) {
		validate(transaction);
		return new TransactionResult();
	}

	protected void validate(Transaction transaction) {
		if (transaction == null) {
			throw new IllegalArgumentException("Transaction was null");
		}

		if (transaction.getAmount() == null) {
			throw new IllegalArgumentException("Transaction.Amount was null");
		}

		if (!isValid(transaction.getAmount())) {
			throw new IllegalArgumentException("Transaction.Amount was null or invalid: " + transaction.getAmount());
		}

		if (transaction.getCard() == null) {
			throw new IllegalArgumentException("Transaction.Card was null");
		}

		if (transaction.getCard().getNumber() == null) {
			throw new IllegalArgumentException("Transaction.Card.Number was null");
		}

		if (transaction.getCard().getExpiration() == null) {
			throw new IllegalArgumentException("Transaction.Card.Expiration was null");
		}

		if (transaction.getCard().getExpiration().getMonth() == null) {
			throw new IllegalArgumentException("Transaction.Card.Expiration.Month was null");
		}

		if (transaction.getCard().getExpiration().getYear() == null) {
			throw new IllegalArgumentException("Transaction.Card.Expiration.Year was null");
		}
	}

	private boolean isValid(BigDecimal value) {
		if (value == null) {
			return false;
		}

		return value.compareTo(BigDecimal.ZERO) > 0;
	}

}
