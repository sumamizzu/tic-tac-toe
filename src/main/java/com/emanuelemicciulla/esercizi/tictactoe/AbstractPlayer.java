package com.emanuelemicciulla.esercizi.tictactoe;

import java.io.IOException;

public abstract class AbstractPlayer implements Player {

	private char mark;
	/**
	 * @return the mark
	 */
	public char getMark() {
		return mark;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	private String name;

	private UserInterface ui;

	private Board board;

	@Override
	public void init(String string, char c, UserInterface ui, Board board) {
		if(name!=null)throw new UnsupportedOperationException("Player already initialized");
		this.name = string;
		this.mark = c;
		this.ui = ui;
		this.board = board;
	}

	/**
	 * @return the board
	 */
	protected Board getBoard() {
		return board;
	}

	protected UserInterface getUi() {
		return ui;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + getName();
	}

	@Override
	public void close() throws IOException {
		//do nothing
	}
}
