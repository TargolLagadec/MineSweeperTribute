package org.targol.mine.ui.components;

import org.targol.mine.game.Cell;

public class HexCellView extends AbstractCellView {

	public static final double CELL_HEIGHT_AND_WIDTH = 21d;

	public HexCellView(final Cell cell, final int row, final int col) {
		super(cell, row, col);
		getStyleClass().add("hex-cell"); //$NON-NLS-1$
		refresh();
	}

	@Override
	protected double getCellDim() {
		return CELL_HEIGHT_AND_WIDTH;
	}

}
