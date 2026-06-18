package org.targol.mine.ui.panels;

import org.targol.mine.game.Cell;
import org.targol.mine.game.HexMineField;
import org.targol.mine.game.IMineField;
import org.targol.mine.game.IMineFieldListener;
import org.targol.mine.ui.components.HexCellView;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class HexMineFieldPanel extends Pane {

	private final IMineFieldListener listener;
	private final HexMineField field;
	private final HexCellView[][] cellViews;
	private final IntegerProperty mineCount = new SimpleIntegerProperty();
	private boolean gameStarted = false;

	public HexMineFieldPanel(final IMineField field, final IMineFieldListener listener) {
		super();
		this.field = (HexMineField) field;
		this.listener = listener;
		this.mineCount.set(field.getNbMines());
		this.cellViews = new HexCellView[field.getRowCount()][field.getColumnCount()];
		for (int r = 0; r < field.getRowCount(); r++) {
			for (int c = 0; c < field.getColumnCount(); c++) {
				final Cell cell = field.getCell(r, c);
				final HexCellView cellView = new HexCellView(cell, r, c);
				this.cellViews[r][c] = cellView;
				cellView.setOnMouseClicked(event -> {
					onCellClicked(cellView.getRow(), cellView.getColumn(), event);
				});
				if (r == 0 || r % 2 == 0) {
					cellView.relocate(30 * c, r * 10);
				} else {
					cellView.relocate(30 * c + 15, r * 10);
				}
				getChildren().add(cellView);
			}
		}
	}

	private void onCellClicked(final int row, final int column, final MouseEvent event) {
		if (!this.gameStarted) {
			this.gameStarted = true;
			this.listener.gameStarted();
		}
		if (event.getButton() == MouseButton.PRIMARY) {
			this.field.reveal(row, column);
			for (final Node node : getChildren()) {
				((HexCellView) node).refresh();
			}
			if (this.field.isGameLost()) {
				this.listener.gameLost();
			}
		} else if (event.getButton() == MouseButton.SECONDARY) {
			final Cell cell = this.field.getCell(row, column);
			if (cell.isFlagged()) {
				cell.setFlagged(false);
				this.mineCount.set(this.mineCount.get() + 1);
				this.listener.mineCounterChanged(this.mineCount.get());
			} else {
				cell.setFlagged(true);
				this.mineCount.set(this.mineCount.get() - 1);
				this.listener.mineCounterChanged(this.mineCount.get());
			}
			this.cellViews[row][column].refresh();
		}
	}

}
