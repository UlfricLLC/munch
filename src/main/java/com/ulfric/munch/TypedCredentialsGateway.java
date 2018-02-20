package com.ulfric.munch;

public abstract class TypedCredentialsGateway<T> extends SkeletalGateway {

	public TypedCredentialsGateway(Credentials credentials) {
		super(credentials);
	}

	protected final T credentials() {
		return credentials(credentials);
	}

	protected abstract T credentials(Credentials credentials);

}
