package org.targol.mine.game;

public class Cell {
	private boolean mine = false;
	private boolean revealed = false;
	private boolean flagged = false;
	private int adjacentMineCount = 0;
	private final int col;
	private final int row;

	public Cell(final int row, final int col) {
		this.col = col;
		this.row = row;
	}

	public int getCol() {
		return this.col;
	}

	public int getRow() {
		return this.row;
	}

	public boolean isMine() {
		return this.mine;
	}

	public void setMine(final boolean mine) {
		this.mine = mine;
	}

	public boolean isRevealed() {
		return this.revealed;
	}

	public void setRevealed(final boolean revealed) {
		this.revealed = revealed;
	}

	public boolean isFlagged() {
		return this.flagged;
	}

	public void setFlagged(final boolean flagged) {
		this.flagged = flagged;
	}

	public int getAdjacentMineCount() {
		return this.adjacentMineCount;
	}

	public void incrementAdjacentMineCount() {
		this.adjacentMineCount++;
	}
}
