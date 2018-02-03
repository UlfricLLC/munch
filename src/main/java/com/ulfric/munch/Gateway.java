package com.ulfric.munch;

import com.ulfric.munch.model.Transaction;
import com.ulfric.munch.model.TransactionResult;

public interface Gateway {

	TransactionResult process(Transaction transaction);

}
