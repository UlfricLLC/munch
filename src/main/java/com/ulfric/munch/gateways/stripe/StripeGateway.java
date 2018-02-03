package com.ulfric.munch.gateways.stripe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import com.stripe.net.RequestOptions.RequestOptionsBuilder;
import com.ulfric.munch.Credentials;
import com.ulfric.munch.SkeletalGateway;
import com.ulfric.munch.model.Address;
import com.ulfric.munch.model.Card;
import com.ulfric.munch.model.Customer;
import com.ulfric.munch.model.Error;
import com.ulfric.munch.model.Transaction;
import com.ulfric.munch.model.TransactionResult;
import com.ulfric.munch.util.CollectionUtil;
import com.ulfric.munch.util.MonetaryUtil;
import com.ulfric.munch.util.StringUtil;
import com.ulfric.munch.util.TemporalUtil;

public class StripeGateway extends SkeletalGateway {

	public StripeGateway(Credentials credentials) {
		super(credentials);
	}

	@Override
	public TransactionResult process(Transaction transaction) {
		TransactionResult result = super.process(transaction);

		RequestOptions options = options();

		Map<String, Object> request = new HashMap<>();
		request.put("amount", MonetaryUtil.toOneString(transaction.getAmount()));
		request.put("currency", MonetaryUtil.currencyCode(transaction.getAmount()));

		Map<String, String> metadata = new HashMap<>();
		metadata.put("api", "munch");
		request.put("metadata", metadata);

		Map<String, String> source = new HashMap<>();
		source.put("object", "card");
		request.put("source", source);

		Card card = transaction.getCard();
		source.put("number", card.getNumber());
		source.put("exp_month", TemporalUtil.twoDigit(card.getExpiration().getMonth()));
		source.put("exp_year", String.valueOf(card.getExpiration().getYear().getValue()));

		if (StringUtil.isNotBlank(card.getSecurityCode())) {
			source.put("cvc", card.getSecurityCode());
		}

		Customer customer = transaction.getCustomer();
		if (customer != null) {
			if (StringUtil.isNotBlank(customer.getEmail())) {
				request.put("receipt_email", customer.getEmail());
				metadata.put("email", customer.getEmail());
			}

			if (StringUtil.isNotBlank(customer.getPhone())) {
				metadata.put("phone", customer.getPhone());
			}

			if (StringUtil.isNotBlank(customer.getIp())) {
				metadata.put("ip", customer.getIp());
			}

			if (StringUtil.isNotBlank(customer.getName())) {
				source.put("name", customer.getName());
			}

			Address address = customer.getAddress();
			if (address != null) {
				if (StringUtil.isNotBlank(address.getCountry())) {
					source.put("address_country", address.getCountry());
				}

				if (StringUtil.isNotBlank(address.getCity())) {
					source.put("address_city", address.getCity());
				}

				if (StringUtil.isNotBlank(address.getZip())) {
					source.put("address_zip", address.getZip());
				}

				if (StringUtil.isNotBlank(address.getRegion())) {
					source.put("address_state", address.getRegion());
				}

				if (StringUtil.isNotBlank(address.getLine1())) {
					source.put("address_line1", address.getLine1());
				}

				if (StringUtil.isNotBlank(address.getLine2())) {
					source.put("address_line2", address.getLine2());
				}
			}
		}

		if (StringUtil.isNotBlank(transaction.getStatementDescriptor())) {
			request.put("statement_descriptor", transaction.getStatementDescriptor());
		}

		if (StringUtil.isNotBlank(transaction.getDescription())) {
			request.put("description", transaction.getDescription());
		}

		if (CollectionUtil.isNotEmpty(transaction.getMetadata())) {
			metadata.putAll(transaction.getMetadata());
		}

		try {
			Charge.create(request, options);
			result.setStatus(true);
		} catch (CardException exception) {
			result.setStatus(false);

			Error error = new Error();
			error.setCode("STRIPE_" + exception.getCode());
			error.setMessage(exception.getMessage());
			List<Error> errors = new ArrayList<>();
			errors.add(error);
			result.setErrors(errors);
		} catch (AuthenticationException | APIException | APIConnectionException | InvalidRequestException exception) {
			result.setStatus(false);

			Error error = new Error();
			error.setCode("STRIPE_" + exception.getStatusCode());
			error.setMessage(exception.getMessage());
			List<Error> errors = new ArrayList<>();
			errors.add(error);
			result.setErrors(errors);
		}

		return result;
	}

	private RequestOptions options() {
		return new RequestOptionsBuilder()
				.setApiKey(credentials.get("secret"))
				.setStripeVersion(credentials.get("version"))
				.setStripeAccount(credentials.get("account"))
				.build();
	}

}
