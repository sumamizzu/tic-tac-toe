/**
 * 
 */
package com.emanuelemicciulla.esercizi.tictactoe;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author Emanuele
 *
 */
public class Game {

	private Board board;
	private Player[] player;
	private int turn;
	private UserInterface ui;
	private int gamesToPlay;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game();
		try {
			game.init();
			game.play();
			game.over();
		} catch (BoardException e) {
			System.err.println("FATAL ERROR: "+e.getMessage());
			System.err.println(game.toString());
			e.printStackTrace();
		}
	}

	private void over() {

		try {
			player[0].close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			player[1].close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ui.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void play() throws BoardException {
		while(0!=gamesToPlay--) {
			while(!board.isOver()) {
				turn=(turn+1)%2;
				ui.alert(player[turn].toString()+"'s turn");
				player[turn].play();
			}
			ui.alert(board.isWon()?"Good game "+player[turn]:("A boring game."));
			board.reset();
		}

		if(player[0]instanceof AI && player[1] instanceof AI && !board.isWon()) {
			ui.prompt("\n\n\n\n\nGREETINS PROFESSOR FALKEN\n\nHELLO\n\nA STRANGE GAME.\nTHE ONLY WINNING MOVE IS NOT TO PLAY.\n\nHOW ABOUT A NICE GAME OF CHESS?\n");
			ui.alert("https://www.youtube.com/watch?v=v11Y64dnnF4");
		}
	}

	private void init() {
		ui = new TextUi();
		board = new Board();
		ui.init(board);
		player = greet(ui);
		String player1 = ui.prompt("Please insert player-1's name");
		player1=player1.trim().isEmpty()?"Player 1":player1;
		String player2 = ui.prompt("Please insert player-2's name");		
		player2=player2.trim().isEmpty()?"Player 2":player2;
		boolean flipCoinForMark = flipCoin();
		player[0].init(player1, flipCoinForMark?Board.X:Board.O, ui, board);
		player[1].init(player2, flipCoinForMark?Board.O:Board.X, ui, board);
		turn = flipCoin()?0:1;
		String numberOfGames = "";
//		while(!numberOfGames.matches("^\\d\\d?$")) {
//			numberOfGames = ui.prompt("How many games?");
//		}
		gamesToPlay = 999999;//Integer.parseInt(numberOfGames);
	}

	private boolean flipCoin() {
		boolean flipCoin;
		flipCoin = Math.random() < 0.5;
		return flipCoin;
	}

	private Player[] greet(UserInterface ui) {
		ui.alert(	"**************************************\n" +
					"*** WELCOME TO MANNY'S TIC TAC TOE ***\n" +
					"**************************************\n" );
		int playersNumber = -1;
		while (playersNumber == -1) {
			String input = ui.prompt("Number of players: (1 or 2)");
			if (input.length() == 1) {
				char inputChar = input.toUpperCase().charAt(0);
				switch (inputChar) {
				case '0':
					return new Player[] { new AI(), new AI() };
				case '1':
					return new Player[] { new Human(), new AI() };
				case '2':
					return new Player[] { new Human(), new Human() };
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MessageFormat.format("Game [board={0}, player={1}, nextMove={2}, ui={3}]",
										board, Arrays.deepToString(player), turn, ui);
	}

}
