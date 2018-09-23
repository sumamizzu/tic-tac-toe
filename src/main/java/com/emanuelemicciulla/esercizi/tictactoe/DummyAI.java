package com.emanuelemicciulla.esercizi.tictactoe;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DummyAI extends AbstractPlayer {

	private static final List<Integer> MOVES_LIST = Arrays.asList(new Integer[] {
			0,1,2,
			3,4,5,
			6,7,8
	});

	@Override
	public void play() throws BoardException {
		char[][] boardState = getBoard().getBoardState();
		LinkedList<Integer> moves = new LinkedList<Integer>(MOVES_LIST);
		Collections.shuffle(moves);
		boolean moved = false;
		while(!moved) {
			int move = moves.poll();
			int c = move%3;
			int r = move/3;
			if(boardState[r][c]==Board.BLANK) {
				getBoard().set(r, c, getMark());
				moved = true;
			}
		}
	}
}
