package org.targol.mine.ui.components;

import org.targol.mine.game.Cell;

public class SquareCellView extends AbstractCellView {

	public static final double CELL_HEIGHT_AND_WIDTH = 20d;

	public SquareCellView(final Cell cell, final int row, final int col) {
		super(cell, row, col);
		refresh();
	}

	@Override
	protected double getCellDim() {
		return CELL_HEIGHT_AND_WIDTH;
	}
}
