package com.payoneer.jobmanagement.exceptions;

public class DataLoadJobException extends Exception {

	private static final long serialVersionUID = 1399080348183630735L;

	public DataLoadJobException(Throwable error) {
		super(error.getLocalizedMessage(), error);
	}
}
