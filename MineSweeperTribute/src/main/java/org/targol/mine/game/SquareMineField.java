package org.targol.mine.game;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import org.targol.mine.game.enums.CellDisplayState;
import org.targol.mine.game.enums.Difficulty;

public class SquareMineField implements IMineField {

	private final Cell[][] cells;
	private final int rowCount;
	private final int columnCount;
	private final int nbMines;
	private boolean gameLost = false;

	public SquareMineField(final int nbRows, final int nbCols, final Difficulty difficulty) {
		this.rowCount = nbRows;
		this.columnCount = nbCols;
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
			final int col = rnd.nextInt(this.columnCount);
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
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = column - 1; c <= column + 1; c++) {
				if (r < 0 || r >= this.rowCount) {
					continue;
				}
				if (c < 0 || c >= this.columnCount) {
					continue;
				}
				if (r == row && c == column) {
					continue;
				}
				final Cell neighbour = this.cells[r][c];
				neighbour.incrementAdjacentMineCount();
			}
		}
		return true;
	}

	private void initFieldWithEmptyCells() {
		for (int r = 0; r < this.rowCount; r++) {
			for (int c = 0; c < this.columnCount; c++) {
				this.cells[r][c] = new Cell(r, c);
			}
		}
	}

	@Override
	public void reveal(final int startRow, final int startColumn) {
		final Queue<Position2D> queue = new ArrayDeque<>();
		queue.add(new Position2D(startRow, startColumn));

		while (!queue.isEmpty()) {
			final Position2D point = queue.remove();
			final int row = point.row();
			final int column = point.column();
			final Cell cell = this.cells[row][column];
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
			for (int r = row - 1; r <= row + 1; r++) {
				for (int c = column - 1; c <= column + 1; c++) {
					if (r < 0 || r >= this.rowCount) {
						continue;
					}
					if (c < 0 || c >= this.columnCount) {
						continue;
					}
					if (r == row && c == column) {
						continue;
					}
					queue.add(new Position2D(r, c));
				}
			}
		}
	}

	private boolean areAllAdjacentMinesFlagged(final int row, final int col) {
		int adjacentFlagsCount = 0;
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				if (r < 0 || r >= this.rowCount) {
					continue;
				}
				if (c < 0 || c >= this.columnCount) {
					continue;
				}
				if (r == row && c == col) {
					continue;
				}
				if (this.cells[r][c].isFlagged()) {
					adjacentFlagsCount++;
				}
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
		return this.columnCount;
	}

	@Override
	public int getNbMines() {
		return this.nbMines;
	}

	@Override
	public String toString() {
		String ret = "MineField of ".concat(Integer.toString(this.rowCount)).concat(" rows and ") //$NON-NLS-1$ //$NON-NLS-2$
				.concat(Integer.toString(this.columnCount)).concat(" columns containing ") //$NON-NLS-1$
				.concat(Integer.toString(this.nbMines)).concat(" mines\n"); //$NON-NLS-1$
		for (int r = 0; r < this.rowCount; r++) {
			for (int c = 0; c < this.columnCount; c++) {
				final Cell cell = this.cells[r][c];
				if (cell.isMine()) {
					ret = ret.concat(CellDisplayState.MINE.getLabel());
				} else {
					ret = ret.concat(Integer.toString(cell.getAdjacentMineCount()));
				}
			}
			ret = ret.concat("\n"); //$NON-NLS-1$
		}
		return ret;
	}

}
