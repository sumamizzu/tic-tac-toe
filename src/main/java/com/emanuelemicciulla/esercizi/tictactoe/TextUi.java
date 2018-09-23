package com.emanuelemicciulla.esercizi.tictactoe;

import java.io.Closeable;
import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;

public class TextUi extends AbstractUserInterface {

	private Scanner sc = new Scanner(System.in);

	@Override
	public void alert(String message) {
		System.out.println(message);
	}

	@Override
	public String prompt(String message) {
		alert(message);
		return sc.nextLine();
	}

	@Override
	public void update(Observable o, Object arg) {
		//alert((getBoard().isWon()?"The winner is:":"Last player:")+ getBoard().getLastPlayerMark());
		alert(" ___\n"
				+ "|" + String.valueOf(getBoard().getBoardState()[Board.TOP]) + "|\n"
				+ "|" + String.valueOf(getBoard().getBoardState()[Board.CENTER]) + "|\n"
				+ "|" + String.valueOf(getBoard().getBoardState()[Board.BOTTOM]) + "|\n"
				+" \u203E\u203E\u203E\n");
	}

	@Override
	public int[] getMove(String message) {
		alert(message);
		return new int[] {getRow(),getColumn()};
	}

	private int getColumn() {
		int column = -1;
		while(column == -1) {
			String input = prompt("column? [L]eft [C]enter or [R]ight");
			if(input.length()==1) {
				char inputChar = input.toUpperCase().charAt(0);
				switch (inputChar) {
					case 'L': return 0;
					case 'C': return 1;
					case 'R': return 2;
				}
			}
		}
		return column;
	}

	private int getRow() {
		int row = -1;
		while(row == -1) {
			String input = prompt("row? [T]op [C]enter or [B]ottom");
			if(input.length()==1) {
				char inputChar = input.toUpperCase().charAt(0);
				switch (inputChar) {
					case 'T': return 0;
					case 'C': return 1;
					case 'B': return 2;
				}
			}
		}
		return row;
	}

	@Override
	public void close() throws IOException {
		sc.close();		
	}

}
