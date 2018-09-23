package com.emanuelemicciulla.esercizi.tictactoe;

import java.util.Arrays;
import java.util.Observable;

import static com.emanuelemicciulla.esercizi.tictactoe.ErrorMessages.*;

public class Board extends Observable {

	public static final int BOTTOM = 2;
	public static final int RIGHT = BOTTOM;
	public static final int LEFT = 0;
	public static final int TOP = LEFT;
	public static final int CENTER = 1;
	public final static char BLANK = ' ';
	public final static char X = 'X';
	public final static char O = 'O';

	private char lastPlayerMark;
	private final char[][] boardState = new char[3][3];
	private final transient char[][] outputState = new char[3][3];
	private boolean outputValid;
	private boolean won;
	private int totalMove;

	private static final char[][] EMPTY_TEMPLATE = new char[][] {
		{ BLANK, BLANK, BLANK },
		{ BLANK, BLANK, BLANK },
		{ BLANK, BLANK, BLANK }
	};

	public Board() {
		reset();
	}

	public void set(int row, int column, char playerMark) throws BoardException {
		checkMove(row, column, playerMark);
		boardState[row][column] = playerMark;
		lastPlayerMark = playerMark;
		outputValid = false;
		won=checkWin(row, column, playerMark,boardState);
		totalMove++;
        setChanged();
        notifyObservers(won);
	}

	private void checkMove(int row, int column, char playerMark) throws BoardException {
		if(playerMark==lastPlayerMark)
			throw new BoardException(DOUBLE_MOVE);
		if (row < TOP || row > BOTTOM)
			throw new BoardException(BAD_ROW);
		if (column < LEFT || column > RIGHT)
			throw new BoardException(BAD_COLUMN);
		if (playerMark != X && playerMark != O)
			throw new BoardException(INVALID_PLAYER_MARK);
		if (boardState[row][column] != BLANK)
			throw new BoardException(BAD_MOVE);
		if (won)
			throw new BoardException(GAME_OVER);
	}

	public static boolean checkWin(int row, int column, char playerMark, char[][] boardState) {
		return (//row win
				boardState[row][(column+1)%3]==playerMark &&
				boardState[row][(column+2)%3]==playerMark
			) || (//column win
				boardState[(row+1)%3][column]==playerMark &&
				boardState[(row+2)%3][column]==playerMark
			) || (row==column && //1st diagonal win
				boardState[(row+1)%3][(column+1)%3]==playerMark &&
				boardState[(row+2)%3][(column+2)%3]==playerMark
			) || (row==2-column &&//2nd diagonal win
					boardState[(row+1)%3][(column+2)%3]==playerMark &&
					boardState[(row+2)%3][(column+1)%3]==playerMark
			);
	}

	public void reset() {
		System.arraycopy(EMPTY_TEMPLATE[0], 0, boardState[0], 0, EMPTY_TEMPLATE[0].length);
		System.arraycopy(EMPTY_TEMPLATE[1], 0, boardState[1], 0, EMPTY_TEMPLATE[1].length);
		System.arraycopy(EMPTY_TEMPLATE[2], 0, boardState[2], 0, EMPTY_TEMPLATE[2].length);
		won = false;
		lastPlayerMark = BLANK;
		outputValid = false;
		totalMove = 0;
	}

	/**
	 * @return the boardState
	 */
	public char[][] getBoardState() {
		if(!outputValid) {
			System.arraycopy(boardState[0], 0, outputState[0], 0, boardState[0].length);
			System.arraycopy(boardState[1], 0, outputState[1], 0, boardState[1].length);
			System.arraycopy(boardState[2], 0, outputState[2], 0, boardState[2].length);
		}
		return outputState;
	}

	/**
	 * @return the won
	 */
	public boolean isWon() {
		return won;
	}

	/**
	 * @return check if isWon or totalMove is 9
	 */
	public boolean isOver() {
		return isWon()||totalMove==9;
	}

	/**
	 * @return the lastPlayerMark
	 */
	public char getLastPlayerMark() {
		return lastPlayerMark;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + Arrays.deepToString(boardState).replaceAll("\\[+", "\n").replaceAll("[\\],]+", " ")+lastPlayerMark;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(boardState);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.deepEquals(boardState, other.boardState))
			return false;
		return true;
	}

}
