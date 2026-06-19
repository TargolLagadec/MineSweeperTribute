package org.targol.mine.ui.components;

import org.targol.mine.game.Cell;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SquareCellView extends AbstractCellView {

	public static final double CELL_HEIGHT_AND_WIDTH = 20d;

	public SquareCellView(final Cell cell, final int row, final int col) {
		super(cell, row, col);
	}

	@Override
	protected double getCellDim() {
		return CELL_HEIGHT_AND_WIDTH;
	}

	@Override
	protected void drawCell(final CellDisplayState state) {
		this.gc.setFill(state.getBgColor());
		// background
		this.gc.fillRect(0, 0, CELL_HEIGHT_AND_WIDTH, CELL_HEIGHT_AND_WIDTH);
		// border
		this.gc.setStroke(Color.BLACK);
		this.gc.setLineWidth(1);
		this.gc.strokeRect(0, 0, CELL_HEIGHT_AND_WIDTH, CELL_HEIGHT_AND_WIDTH);
		// label (if any)
		this.gc.setFont(Font.font("monospace", FontWeight.BOLD, 18));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText(state.getLabel(), 5, 17);
	}
}
