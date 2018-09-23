package com.emanuelemicciulla.esercizi.tictactoe;

import java.io.Closeable;

public interface UserInterface extends Closeable {

	void init(Board board);

	void alert(String string);

	String prompt(String string);

	int[] getMove(String message);

}
