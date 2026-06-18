package org.targol.mine.game;

public interface IMineFieldListener {

	void mineCounterChanged(int remainingMines);

	void gameStarted();

	void gameLost();
}