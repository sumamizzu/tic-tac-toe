package com.emanuelemicciulla.esercizi.tictactoe;

import static org.junit.Assert.*;

import org.junit.After;

import static org.hamcrest.CoreMatchers.*;

import static com.emanuelemicciulla.esercizi.tictactoe.Board.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BoardTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Board board;
	private Board board2;

	@Before
	public void setUp() throws Exception {
		board = new Board();
		board2 = new Board();
	}

	@After
	public void shutDown() throws Exception {
		System.out.println(board);
	}

	@Test
	public void testSet() throws BoardException {
		board.set(CENTER, CENTER, X);
		board.set(BOTTOM, RIGHT, O);
	}

	@Test
	public void testSetBoardExceptionDoubleMove() throws BoardException {
		board.set(CENTER, CENTER, X);
	    thrown.expect(BoardException.class);
	    thrown.expectMessage(ErrorMessages.DOUBLE_MOVE);
	    board.set(BOTTOM, RIGHT, X);
	}

	@Test
	public void testSetBoardExceptionInvalidPlayer() throws BoardException {
	    thrown.expect(BoardException.class);
	    thrown.expectMessage(ErrorMessages.INVALID_PLAYER_MARK);
	    board.set(BOTTOM, RIGHT, 'K');
	}

	@Test
	public void testSetBoardExceptionBadColumn() throws BoardException {
	    thrown.expect(BoardException.class);
	    thrown.expectMessage(ErrorMessages.BAD_COLUMN);
	    board.set(BOTTOM, 3, X);
	}

	@Test
	public void testSetBoardExceptionBadColumn1() throws BoardException {
	    thrown.expect(BoardException.class);
	    thrown.expectMessage(ErrorMessages.BAD_COLUMN);
	    board.set(BOTTOM, -1, X);
	}

	@Test
	public void testSetBoardExceptionBadRow() throws BoardException {
	    thrown.expect(BoardException.class);
	    thrown.expectMessage(ErrorMessages.BAD_ROW);
	    board.set(3, RIGHT, X);
	}

	@Test
	public void testSetBoardExceptionBadRow1() throws BoardException {
	    thrown.expect(BoardException.class);
	    thrown.expectMessage(ErrorMessages.BAD_ROW);
	    board.set(-1, RIGHT, X);
	}

	@Test
	public void testSetBoardExceptionBadMove() throws BoardException {
		board.set(BOTTOM, RIGHT, X);
	    thrown.expect(BoardException.class);
	    thrown.expectMessage(ErrorMessages.BAD_MOVE);
	    board.set(BOTTOM, RIGHT, O);
	}

	@Test
	public void testHashCode() throws BoardException {
		final int initialHshCode = board.hashCode();
		assertThat( board2.hashCode(), is ( initialHshCode ) );

		board.set(TOP, LEFT, X);
		board2.set(TOP, LEFT, X);
		final int firsStepHashCode = board.hashCode();
		assertThat( board2.hashCode(), is ( firsStepHashCode ) );

		board.set(CENTER, CENTER, 'O');
		board2.set(CENTER, CENTER, 'O');
		int secondStepHashCode = board.hashCode();
		assertThat( board2.hashCode(), is ( secondStepHashCode ) );

		assertThat( initialHshCode, is ( not ( firsStepHashCode ) ) );
		assertThat( initialHshCode, is ( not ( secondStepHashCode ) ) );
		assertThat( firsStepHashCode, is ( not ( secondStepHashCode ) ) );
	}

	@Test
	public void testEqualsObject() throws BoardException {
		assertTrue(board.equals(board));
		assertTrue(board2.equals(board2));

		assertTrue(board.equals(board2));
		assertTrue(board2.equals(board));

		board.set(TOP, LEFT, X);
		assertFalse(board.equals(board2));
		assertFalse(board2.equals(board));

		board2.set(TOP, LEFT, X);
		assertTrue(board.equals(board2));
		assertTrue(board2.equals(board));

		board.set(CENTER, CENTER, 'O');
		assertFalse(board.equals(board2));
		assertFalse(board2.equals(board));

		board2.set(CENTER, CENTER, 'O');
		assertTrue(board.equals(board2));
		assertTrue(board2.equals(board));

		//identity check
		assertTrue(board.equals(board));
		assertTrue(board2.equals(board2));
	}

	@Test
	public void testReset() throws BoardException {
		int initialHashCode = board.hashCode();
		board.set(BOTTOM, RIGHT, X);
		board.reset();
		assertEquals(initialHashCode, board.hashCode());
		assertTrue(board.equals(board2));
		board.set(TOP, CENTER, O);
		board.reset();
		assertEquals(initialHashCode, board.hashCode());
		assertTrue(board.equals(board2));
	}

	@Test
	public void testGetWinner() throws BoardException {
		board.set(TOP, LEFT, X);
		board.set(TOP, RIGHT, O);
		board.set(CENTER, LEFT, X);
		board.set(BOTTOM, LEFT, O);
		board.set(CENTER, CENTER, X);
		board.set(BOTTOM, RIGHT, O);
		board.set(CENTER, RIGHT, X);
		assertTrue(board.isWon());
	    thrown.expect(BoardException.class);
	    thrown.expectMessage(ErrorMessages.GAME_OVER);
		board.set(BOTTOM, CENTER, O);
	}

	@Test
	public void testGetWinner1() throws BoardException {
		board.set(TOP, LEFT, X);
		board.set(TOP, RIGHT, O);
		board.set(CENTER, LEFT, X);
		board.set(CENTER, RIGHT, O);
		board.set(BOTTOM, LEFT, X);
		assertTrue(board.isWon());
	}

	@Test
	public void testGetWinner2() throws BoardException {
		board.set(TOP, LEFT, X);
		board.set(TOP, RIGHT, O);
		board.set(CENTER, CENTER, X);
		board.set(CENTER, RIGHT, O);
		board.set(BOTTOM, RIGHT, X);
		assertTrue(board.isWon());
	}

	@Test
	public void testGetWinner2Bis() throws BoardException {
		board.set(BOTTOM, RIGHT, X);
		board.set(TOP, RIGHT, O);
		board.set(CENTER, CENTER, X);
		board.set(CENTER, RIGHT, O);
		board.set(TOP, LEFT, X);
		assertTrue(board.isWon());
	}

	@Test
	public void testGetWinner3() throws BoardException {
		board.set(BOTTOM, LEFT, X);
		board.set(BOTTOM, RIGHT, O);
		board.set(CENTER, CENTER, X);
		board.set(CENTER, RIGHT, O);
		board.set(TOP, RIGHT, X);
		assertTrue(board.isWon());
	}

}
