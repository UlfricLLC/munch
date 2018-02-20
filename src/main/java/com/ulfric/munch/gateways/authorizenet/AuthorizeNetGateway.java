package com.ulfric.munch.gateways.authorizenet;

import com.ulfric.munch.Credentials;
import com.ulfric.munch.TypedCredentialsGateway;
import com.ulfric.munch.model.Address;
import com.ulfric.munch.model.Card;
import com.ulfric.munch.model.Customer;
import com.ulfric.munch.model.Expiration;
import com.ulfric.munch.model.Transaction;
import com.ulfric.munch.model.TransactionResult;
import com.ulfric.munch.util.AddressUtil;
import com.ulfric.munch.util.TemporalUtil;

import net.authorize.api.contract.v1.CreditCardType;
import net.authorize.api.contract.v1.CustomerAddressType;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionTypeEnum;

public class AuthorizeNetGateway extends TypedCredentialsGateway<MerchantAuthenticationType> {

	public AuthorizeNetGateway(Credentials credentials) {
		super(credentials);
	}

	@Override
	public TransactionResult process(Transaction transaction) {
		TransactionResult result = super.process(transaction);

		//MerchantAuthenticationType auth = credentials();

		PaymentType paymentType = new PaymentType();
		CreditCardType creditCard = new CreditCardType();
		Card card = transaction.getCard();
		creditCard.setCardNumber(card.getNumber());
		creditCard.setCardCode(card.getSecurityCode());
		Expiration expiration = card.getExpiration();
		creditCard.setExpirationDate(TemporalUtil.mmyy(expiration.getMonth(), expiration.getYear()));
		paymentType.setCreditCard(creditCard);

		TransactionRequestType request = new TransactionRequestType();
		request.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
		request.setPayment(paymentType);
		request.setAmount(transaction.getAmount());
		request.setMerchantDescriptor(transaction.getStatementDescriptor());

		Customer customer = transaction.getCustomer();
		request.setCustomerIP(customer.getIp());

		Address address = customer.getAddress();
		CustomerAddressType customerAddress = new CustomerAddressType();
		customerAddress.setAddress(AddressUtil.singleLineAddress(address.getLine1(), address.getLine2()));
		customerAddress.setZip(address.getZip());
		customerAddress.setCity(address.getCity());
		customerAddress.setState(address.getRegion());
		customerAddress.setCountry(address.getCountry());
		customerAddress.setEmail(customer.getEmail());
		//customerAddress.setFirstName(value);
		//customerAddress.setLastName(value);
		//customerAddress.setPhoneNumber(value);
		request.setBillTo(customerAddress);

		return result;
	}

	@Override
	protected MerchantAuthenticationType credentials(Credentials credentials) {
		MerchantAuthenticationType auth = new MerchantAuthenticationType();
		auth.setName(credentials.get("name"));
		auth.setTransactionKey(credentials.get("transactionkey"));
		return auth;
	}

}
