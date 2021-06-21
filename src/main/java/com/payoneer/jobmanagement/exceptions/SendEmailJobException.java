package com.payoneer.jobmanagement.exceptions;

public class SendEmailJobException extends Exception {

	private static final long serialVersionUID = 2844325562995693863L;

	public SendEmailJobException(Throwable error) {
		super(error.getLocalizedMessage(), error);
	}
}
