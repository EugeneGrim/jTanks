package ru.grim.jtanks.exception;

@SuppressWarnings("serial")
public class StopServerException extends RuntimeException {
	
	public StopServerException(String message) {
		super(message);
	}
}
