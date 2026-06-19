package org.targol.mine.ui.components;

import org.targol.mine.game.Cell;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class AbstractCellView extends Canvas {

	private final Cell cell;
	private final int row;
	private final int column;
	private CellDisplayState previousDisplay;
	protected GraphicsContext gc;

	public AbstractCellView(final Cell cell, final int row, final int column) {
		super();
		this.cell = cell;
		this.row = row;
		this.column = column;
		final double dim = getCellDim();
		setWidth(dim);
		setHeight(dim);
		this.gc = getGraphicsContext2D();
		refresh();
	}

	protected abstract double getCellDim();

	protected abstract void drawCell(final CellDisplayState state);

	public void refresh() {
		if (!this.cell.isRevealed()) {
			if (this.cell.isFlagged()) {
				updateCell(CellDisplayState.FLAGGED);
				return;
			}
			updateCell(CellDisplayState.HIDDEN);
			return;
		}
		if (this.cell.isMine()) {
			updateCell(CellDisplayState.MINE);
			return;
		}
		if (this.cell.getAdjacentMineCount() == 0) {
			updateCell(CellDisplayState.EMPTY);
		} else {
			final String stateValue = "NUMBER_".concat(Integer.toString(this.cell.getAdjacentMineCount()));
			updateCell(CellDisplayState.valueOf(stateValue));
		}

	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	private void updateCell(final CellDisplayState state) {
		if (state.equals(this.previousDisplay)) {
			return;
		}
		this.previousDisplay = state;
		drawCell(state);
	}
}
