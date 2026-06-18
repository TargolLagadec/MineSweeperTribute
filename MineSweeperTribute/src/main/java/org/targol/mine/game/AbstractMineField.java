package org.targol.mine.game;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.targol.mine.game.enums.Difficulty;

public abstract class AbstractMineField implements IMineField {

	protected final Cell[][] cells;
	protected final int rowCount;
	protected final int colCount;
	protected final int nbMines;
	protected boolean gameLost = false;

	public AbstractMineField(final int nbRows, final int nbCols, final Difficulty difficulty) {
		this.rowCount = nbRows;
		this.colCount = nbCols;
		this.cells = new Cell[nbRows][nbCols];
		initFieldWithEmptyCells();
		this.nbMines = nbRows * nbCols / difficulty.getMinesFactor();
		final Random rnd = new Random();
		placeAllMines(rnd);
	}

	private void placeAllMines(final Random rnd) {
		int placedMines = 0;
		while (placedMines < this.nbMines) {
			final int row = rnd.nextInt(this.rowCount);
			final int col = rnd.nextInt(this.colCount);
			if (placeMine(row, col)) {
				placedMines++;
			}
		}
	}

	private boolean placeMine(final int row, final int column) {
		final Cell mineCell = this.cells[row][column];
		if (mineCell.isMine()) {
			return false;
		}
		mineCell.setMine(true);
		for (final Cell neighbour : getAdjacentCells(row, column)) {
			neighbour.incrementAdjacentMineCount();
		}
		return true;
	}

	protected abstract List<Cell> getAdjacentCells(final int row, final int col);

	private void initFieldWithEmptyCells() {
		for (int r = 0; r < this.rowCount; r++) {
			for (int c = 0; c < this.colCount; c++) {
				this.cells[r][c] = new Cell(r, c);
			}
		}
	}

	@Override
	public void reveal(final int startRow, final int startColumn) {
		final Queue<Cell> queue = new ArrayDeque<>();
		queue.add(this.cells[startRow][startColumn]);

		while (!queue.isEmpty()) {
			final Cell cell = queue.remove();
			final int row = cell.getRow();
			final int column = cell.getCol();
			if (cell.isFlagged()) {
				continue;
			}
			if (cell.isRevealed()) {
				continue;
			}
			if (!cell.isFlagged()) {
				cell.setRevealed(true);
				if (cell.isMine()) {
					this.gameLost = true;
					break;
				}
			}
			if (cell.getAdjacentMineCount() != 0 && !areAllAdjacentMinesFlagged(cell.getRow(), cell.getCol())) {
				continue;
			}
			for (final Cell neighbour : getAdjacentCells(row, column)) {
				queue.add(neighbour);
			}
		}
	}

	private boolean areAllAdjacentMinesFlagged(final int row, final int col) {
		int adjacentFlagsCount = 0;
		for (final Cell neighbour : getAdjacentCells(row, col)) {
			if (neighbour.isFlagged()) {
				adjacentFlagsCount++;
			}
		}
		return adjacentFlagsCount == this.cells[row][col].getAdjacentMineCount();
	}

	@Override
	public boolean isGameLost() {
		return this.gameLost;
	}

	@Override
	public Cell[][] getCells() {
		return this.cells;
	}

	@Override
	public Cell getCell(final int row, final int col) {
		return this.cells[row][col];
	}

	@Override
	public int getRowCount() {
		return this.rowCount;
	}

	@Override
	public int getColumnCount() {
		return this.colCount;
	}

	@Override
	public int getNbMines() {
		return this.nbMines;
	}
}
