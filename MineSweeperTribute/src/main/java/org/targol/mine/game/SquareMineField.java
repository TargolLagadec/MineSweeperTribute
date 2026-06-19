package org.targol.mine.game;

import java.util.ArrayList;
import java.util.List;

import org.targol.mine.game.enums.Difficulty;
import org.targol.mine.ui.components.CellDisplayState;

public class SquareMineField extends AbstractMineField {

	public SquareMineField(final int nbRows, final int nbCols, final Difficulty difficulty) {
		super(nbRows, nbCols, difficulty);
	}

	@Override
	protected List<Cell> getAdjacentCells(final int row, final int col) {
		final List<Cell> ret = new ArrayList<>();
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				if (r < 0 || r >= this.rowCount) {
					continue;
				}
				if (c < 0 || c >= this.colCount) {
					continue;
				}
				if (c == col && r == row) {
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

}
