package ru.grim.jtanks.exception;

@SuppressWarnings("serial")
public class ClientAcceptServerException extends RuntimeException {
	
	public ClientAcceptServerException(String message) {
		super(message);
	}
}
