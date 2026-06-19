package org.targol.mine.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.targol.mine.game.enums.Difficulty;

public abstract class AbstractMineField {

	protected final Cell[][] cells;
	protected final int rowCount;
	protected final int colCount;
	protected final int nbMines;
	protected boolean gameLost = false;
	private List<Cell> revealedCells = new ArrayList<>();

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

	public void reveal(final int startRow, final int startColumn) {
		this.revealedCells = new ArrayList<>();
		final Queue<Cell> queue = new ArrayDeque<>();
		queue.add(this.cells[startRow][startColumn]);

		while (!queue.isEmpty()) {
			final Cell cell = queue.remove();
			this.revealedCells.add(cell);
			final int row = cell.getRow();
			final int column = cell.getCol();
			if (cell.isFlagged()) {
				continue;
			}
			if (cell.isRevealed()) {
				if (cell.getAdjacentMineCount() != 0) {
					queue.addAll(getAllAdjacentUnrevealedAndUnFlagedCells(row, column));
				}
				continue;
			}
			if (!cell.isFlagged()) {
				cell.setRevealed(true);
				if (cell.isMine()) {
					this.gameLost = true;
					break;
				}
			}
			if (cell.getAdjacentMineCount() > 0) {
				continue;
			}
			for (final Cell neighbour : getAdjacentCells(row, column)) {
				queue.add(neighbour);
			}
		}
	}

	/**
	 * This method will return the list of all unrevealed and unflagged cells
	 * surrounding a revealed cell with number already surrounded with the correct
	 * ammont of flags.
	 *
	 * @param row row of the numbered Cell to check
	 * @param col col of the numbered Cell to check
	 * @return the list of all unrevealed and unflagged cells surrounding a revealed
	 *         cell with number already surrounded with the correct ammont of
	 *         flags.<br>
	 *         This list is empty if no unrevealed cells are present around this
	 *         cell or the number of flags that surrounds it is not equal to its
	 *         number.
	 */
	private List<Cell> getAllAdjacentUnrevealedAndUnFlagedCells(final int row, final int col) {
		final List<Cell> ret = new ArrayList<>();
		final int expectedFlags = this.cells[row][col].getAdjacentMineCount();
		int adjacentFlagsCount = 0;
		for (final Cell neighbour : getAdjacentCells(row, col)) {
			if (!neighbour.isRevealed()) {
				ret.add(neighbour);
			}
			if (neighbour.isFlagged()) {
				adjacentFlagsCount++;
			}
		}
		if (expectedFlags != adjacentFlagsCount) {
			ret.clear();
		}
		return ret;
	}

	public List<Cell> getRevealedCells() {
		return this.revealedCells;
	}

	public void setRevealedCells(final List<Cell> revealedCells) {
		this.revealedCells = revealedCells;
	}

	public boolean isGameLost() {
		return this.gameLost;
	}

	public Cell[][] getCells() {
		return this.cells;
	}

	public Cell getCell(final int row, final int col) {
		return this.cells[row][col];
	}

	public int getRowCount() {
		return this.rowCount;
	}

	public int getColumnCount() {
		return this.colCount;
	}

	public int getNbMines() {
		return this.nbMines;
	}
}
