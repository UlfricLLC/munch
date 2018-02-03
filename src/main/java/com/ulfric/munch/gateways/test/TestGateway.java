package com.ulfric.munch.gateways.test;

import com.ulfric.munch.Credentials;
import com.ulfric.munch.SkeletalGateway;
import com.ulfric.munch.model.Transaction;
import com.ulfric.munch.model.TransactionResult;

public class TestGateway extends SkeletalGateway {

	public TestGateway() {
		this(new Credentials());
	}

	public TestGateway(Credentials credentials) {
		super(credentials);
	}

	@Override
	public TransactionResult process(Transaction transaction) {
		TransactionResult result = super.process(transaction);
		result.setStatus(true);
		return result;
	}

}
