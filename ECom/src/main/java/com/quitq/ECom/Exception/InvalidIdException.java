package com.quitq.ECom.Exception;

public class InvalidIdException extends Exception {
	private String message;
	public InvalidIdException(String message)
	{
		this.message=message;
		
	}
	public String getMessage()
	{
		return message;
	}

}
