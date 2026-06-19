package org.targol.mine.game;

import java.util.ArrayList;
import java.util.List;

import org.targol.mine.game.enums.Difficulty;
import org.targol.mine.ui.components.CellDisplayState;

public class HexMineField extends AbstractMineField {

	public HexMineField(final int nbRows, final int nbCols, final Difficulty difficulty) {
		super(nbRows, nbCols, difficulty);
	}

	@Override
	protected List<Cell> getAdjacentCells(final int row, final int col) {
		final List<Cell> ret = new ArrayList<>();
		if (row == 0 || row % 2 == 0) {
			for (final DirectionEven direction : DirectionEven.values()) {
				final int r = row + direction.rowDif;
				final int c = col + direction.colDif;
				if (r < 0 || r >= this.rowCount) {
					continue;
				}
				if (c < 0 || c >= this.colCount) {
					continue;
				}
				ret.add(this.cells[r][c]);
			}
		} else {
			for (final DirectionOdd direction : DirectionOdd.values()) {
				final int r = row + direction.rowDif;
				final int c = col + direction.colDif;
				if (r < 0 || r >= this.rowCount) {
					continue;
				}
				if (c < 0 || c >= this.colCount) {
					continue;
				}
				ret.add(this.cells[r][c]);
			}
		}
		return ret;
	}

	@Override
	public String toString() {
		String ret = "MineField of ".concat(Integer.toString(this.rowCount)).concat(" rows and ") //$NON-NLS-1$ //$NON-NLS-2$
				.concat(Integer.toString(this.colCount)).concat(" columns containing ") //$NON-NLS-1$
				.concat(Integer.toString(this.nbMines)).concat(" mines\n"); //$NON-NLS-1$
		for (int r = 0; r < this.rowCount; r++) {
			for (int c = 0; c < this.colCount; c++) {
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

		private DirectionEven(final int rowDif, final int colDif) {
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

		private DirectionOdd(final int rowDif, final int colDif) {
			this.rowDif = rowDif;
			this.colDif = colDif;
		}
	}
}
