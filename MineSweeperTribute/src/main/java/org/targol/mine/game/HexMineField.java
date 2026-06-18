package org.targol.mine.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.targol.mine.game.enums.CellDisplayState;
import org.targol.mine.game.enums.Difficulty;

public class HexMineField implements IMineField {

	private final Cell[][] cells;
	private final int rowCount;
	private final int columnCount;
	private final int nbMines;
	private boolean gameLost = false;

	public HexMineField(final int nbRows, final int nbCols, final Difficulty difficulty) {
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
		for (Cell neighbour : getAdjacentCells(row, column)) {
			neighbour.incrementAdjacentMineCount();
		}
		return true;
	}

	private List<Cell> getAdjacentCells(int row, int col) {
		List<Cell> ret = new ArrayList<>();
		if (row == 0 || row % 2 == 0) {
			for (DirectionEven direction : DirectionEven.values()) {
				int r = row + direction.rowDif;
				int c = col + direction.colDif;
				if (r < 0 || r >= this.rowCount) {
					continue;
				}
				if (c < 0 || c >= this.columnCount) {
					continue;
				}
				ret.add(this.cells[r][c]);
			}
		} else {
			for (DirectionOdd direction : DirectionOdd.values()) {
				int r = row + direction.rowDif;
				int c = col + direction.colDif;
				if (r < 0 || r >= this.rowCount) {
					continue;
				}
				if (c < 0 || c >= this.columnCount) {
					continue;
				}
				ret.add(this.cells[r][c]);
			}
		}
		return ret;
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
//			if (cell.getAdjacentMineCount() != 0) {
//				continue;
//			}
			for (Cell neighbour : getAdjacentCells(row, column)) {
				queue.add(neighbour);
			}
		}
	}

	private boolean areAllAdjacentMinesFlagged(final int row, final int col) {
		int adjacentFlagsCount = 0;
		for (Cell neighbour : getAdjacentCells(row, col)) {
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

	private enum DirectionEven {
		NORTH(-2, 0),
		NORTH_EAST(-1, 0),
		SOUTH_EAST(+1, 0),
		SOUTH(+2, 0),
		SOUTH_WEST(+1, -1),
		NORTH_WEST(-1, -1);

		private final int rowDif;
		private final int colDif;

		private DirectionEven(int rowDif, int colDif) {
			this.rowDif = rowDif;
			this.colDif = colDif;
		}
	}

	private enum DirectionOdd {
		NORTH(-2, 0),
		NORTH_EAST(-1, +1),
		SOUTH_EAST(+1, +1),
		SOUTH(+2, 0),
		SOUTH_WEST(+1, 0),
		NORTH_WEST(-1, 0);

		private final int rowDif;
		private final int colDif;

		private DirectionOdd(int rowDif, int colDif) {
			this.rowDif = rowDif;
			this.colDif = colDif;
		}
	}
}
