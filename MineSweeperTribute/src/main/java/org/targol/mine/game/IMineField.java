package org.targol.mine.game;

public interface IMineField {

	void reveal(int startRow, int startColumn);

	boolean isGameLost();

	Cell[][] getCells();

	Cell getCell(int row, int col);

	int getRowCount();

	int getColumnCount();

	int getNbMines();

}