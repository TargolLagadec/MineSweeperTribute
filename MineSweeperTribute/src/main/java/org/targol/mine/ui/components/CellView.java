package org.targol.mine.ui.components;

import org.targol.mine.game.Cell;
import org.targol.mine.game.enums.CellDisplayState;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class CellView extends StackPane {

	public static final int CELL_HEIGHT_AND_WIDTH = 20;
	private final Cell cell;
	private final int row;
	private final int column;
	private String currentCssClass;
	private final Label label = new Label();

	public CellView(final Cell cell, final int row, final int column) {
		super();
		getChildren().add(this.label);
		this.cell = cell;
		this.row = row;
		this.column = column;
		this.label.getStyleClass().clear();
		this.label.getStyleClass().add("cell-label");
		updateStyle(CellDisplayState.HIDDEN);
		setMinSize(CELL_HEIGHT_AND_WIDTH, CELL_HEIGHT_AND_WIDTH);
		setPrefSize(CELL_HEIGHT_AND_WIDTH, CELL_HEIGHT_AND_WIDTH);
		setMaxSize(CELL_HEIGHT_AND_WIDTH, CELL_HEIGHT_AND_WIDTH);
	}

	public void refresh() {
		if (!this.cell.isRevealed()) {
			if (this.cell.isFlagged()) {
				updateStyle(CellDisplayState.FLAGGED);
				return;
			}
			updateStyle(CellDisplayState.HIDDEN);
			return;
		}
		if (this.cell.isMine()) {
			updateStyle(CellDisplayState.MINE);
			return;
		}
		if (this.cell.getAdjacentMineCount() == 0) {
			updateStyle(CellDisplayState.EMPTY);
		} else {
			final String stateValue = "NUMBER_".concat(Integer.toString(this.cell.getAdjacentMineCount()));
			updateStyle(CellDisplayState.valueOf(stateValue));
		}

	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	private void updateStyle(final CellDisplayState state) {
		if (this.currentCssClass != null) {
			getStyleClass().remove(this.currentCssClass);
		}
		this.currentCssClass = state.getCssClass();
		getStyleClass().add(this.currentCssClass);
		this.label.setText(state.getLabel());
	}
}
