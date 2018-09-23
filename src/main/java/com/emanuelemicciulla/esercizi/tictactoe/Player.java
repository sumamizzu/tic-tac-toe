package com.emanuelemicciulla.esercizi.tictactoe;

import java.io.Closeable;

public interface Player extends Closeable{

	void init(String string, char c, UserInterface ui, Board board);

	void play() throws BoardException;

}
