package ru.grim.jtanks.exception;

@SuppressWarnings("serial")
public class StartServerException extends RuntimeException {
	
	public StartServerException(String message) {
		super(message);
	}
}
