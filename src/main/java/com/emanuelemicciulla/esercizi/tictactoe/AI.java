package com.emanuelemicciulla.esercizi.tictactoe;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AI extends AbstractPlayer {

	private static final String KNOWN_MOVES_SER = "knownmoves.ser";

	private HashMap<String,Integer> knownMoves;

	private static final List<Integer> MOVES_LIST = Arrays.asList(new Integer[] {
			0,1,2,
			3,4,5,
			6,7,8
	});
	
	@Override
	public void init(String string, char c, UserInterface ui, Board board) {
		super.init(string, c, ui, board);
		HashMap<String, Integer> loadedMoves = loadMoves();
		knownMoves = loadedMoves!=null?loadedMoves:new HashMap<>();
		ui.alert("I KNOW "+knownMoves.size()+" MOVES");
	}

	private char[][] newEmptyBoardState() {
		return new char[][] {
						{Board.BLANK,Board.BLANK,Board.BLANK},
						{Board.BLANK,Board.BLANK,Board.BLANK},
						{Board.BLANK,Board.BLANK,Board.BLANK}
					};
	}

	@Override
	public void play() throws BoardException {
		char[][] boardState = getBoard().getBoardState();

		//recall
		Integer move = knownMoves.get(encodeBoardState(boardState));

		if(move == null) {

			move = tryForTrisOrAtLestStopEnemyOne(boardState);

			if(move == null) {
				getUi().alert("I HAVE NO IDEA... LET'S TRY THIS...");
				move = randomMove(boardState);
			} else {
				//remember if not random
				getUi().alert("I WILL REMEMBER THIS");
				knownMoves.put(encodeBoardState(boardState), move);
			}

		}else {
			getUi().alert("I KNOW THIS ONE!");
		}

		getBoard().set(move/3, move%3, getMark());

	}

	private String encodeBoardState(char[][] boardState) {
		char opponentMark = getMark()==Board.X?Board.O:Board.X;
		return Arrays.deepToString(boardState).replace(getMark(), '+').replace(opponentMark, '-');
	}

	private Integer randomMove(char[][] boardState) throws BoardException {
		LinkedList<Integer> moves = new LinkedList<Integer>(MOVES_LIST);
		Collections.shuffle(moves);
		while(!moves.isEmpty()) {
			int move = moves.poll();
			int c = move%3;
			int r = move/3;
			if(boardState[r][c]==Board.BLANK) {
				return move;
			}
		}
		return null;
	}

	private Integer tryForTrisOrAtLestStopEnemyOne(char[][] boardState) {
		Integer stopMove = null;
		char opponentMark = getMark()==Board.X?Board.O:Board.X;
		for(int r = 0; r<3; r++) {
			for(int c=0; c<3; c++) {
				if(boardState[r][c]==Board.BLANK) {
					if(Board.checkWin(r, c, getMark(), boardState)) {
						getUi().alert("AH AH!");
						return r*3+c;
					}else if(stopMove==null&&Board.checkWin(r, c, opponentMark, boardState)){
						getUi().alert("OH NO! I STOP YOU!");
						stopMove = r*3+c;
					}
				}
			}
		}
		return stopMove;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Integer> loadMoves() {
		HashMap<String, Integer> loadedMoves = null;

		try (FileInputStream fin = new FileInputStream(KNOWN_MOVES_SER);
				ObjectInputStream oos = new ObjectInputStream(fin);) {

			loadedMoves = (HashMap<String, Integer>) oos.readObject();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return loadedMoves;
	}

	public void close() throws IOException {
		try {
			HashMap<String, Integer> loadedMoves = loadMoves();
			knownMoves.putAll(loadedMoves);
		} catch (Exception e) {/*NOOP*/}
		try (FileOutputStream fout = new FileOutputStream(KNOWN_MOVES_SER);
				ObjectOutputStream oos = new ObjectOutputStream(fout);) {
			oos.writeObject(knownMoves);
		}
		getUi().alert(knownMoves.size()+" MOVES KNOWN SO FAR!");
	}
}
