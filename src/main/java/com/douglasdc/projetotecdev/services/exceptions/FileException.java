package com.douglasdc.projetotecdev.services.exceptions;

public class FileException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FileException(String msg) {
		super(msg); //super é a classe RuntimeException
	}
	
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
