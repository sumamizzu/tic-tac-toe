package com.emanuelemicciulla.esercizi.tictactoe;

import java.util.Observer;

public abstract class AbstractUserInterface implements UserInterface, Observer {

	private Board board;

	/**
	 * @return the board
	 */
	protected Board getBoard() {
		return board;
	}

	@Override
	public void init(Board board) {
		this.board = board;
		this.board.addObserver(this);
	}

}
