package com.emanuelemicciulla.esercizi.tictactoe;

public class BoardException extends Exception {

	private static final long serialVersionUID = -6217705409703791507L;

	public BoardException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BoardException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public BoardException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public BoardException(Throwable cause) {
		super(cause);
	}

}
