package com.emanuelemicciulla.esercizi.tictactoe;

public class Human extends AbstractPlayer {

	@Override
	public void play() throws BoardException {
		char[][] currentBoardState = getBoard().getBoardState();
		int[] move = getUi().getMove(getName()+" is your turn! Place your "+getMark());
		while(currentBoardState[move[0]][move[1]]!=Board.BLANK) {
			move = getUi().getMove(getName()+" position occupied! retry! ");
		}
		getBoard().set(move[0], move[1], getMark());
	}

}
