package org.targol.mine.ui.components;

import org.targol.mine.game.Cell;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HexCellView extends AbstractCellView {

	public static final double CELL_HEIGHT_AND_WIDTH = 21d;

	public HexCellView(final Cell cell, final int row, final int col) {
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
		this.gc.fillPolygon(new double[] { 05, 15, 20, 15, 05, 00 }, new double[] { 00, 00, 10, 20, 20, 10 }, 6);
		// border
		this.gc.setStroke(Color.BLACK);
		this.gc.setLineWidth(1);
		this.gc.strokePolygon(new double[] { 05, 15, 20, 15, 05, 00 }, new double[] { 00, 00, 10, 20, 20, 10 }, 6);
		// label (if any)
		this.gc.setFont(Font.font("monospace", FontWeight.BOLD, 18));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText(state.getLabel(), 5, 17);
	}
}
