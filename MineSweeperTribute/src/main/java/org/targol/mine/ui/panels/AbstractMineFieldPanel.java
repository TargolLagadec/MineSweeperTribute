package org.targol.mine.ui.panels;

import org.targol.mine.game.Cell;
import org.targol.mine.game.IMineField;
import org.targol.mine.game.IMineFieldListener;
import org.targol.mine.ui.components.AbstractCellView;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public abstract class AbstractMineFieldPanel extends Pane {

	protected final IMineFieldListener listener;
	protected final IMineField field;
	protected final AbstractCellView[][] cellViews;
	protected final IntegerProperty mineCount = new SimpleIntegerProperty();
	protected boolean gameStarted = false;
	protected boolean gameEnded = false;

	public AbstractMineFieldPanel(final IMineField field, final IMineFieldListener listener) {
		super();
		this.field = field;
		this.listener = listener;
		this.mineCount.set(field.getNbMines());
		this.cellViews = new AbstractCellView[field.getRowCount()][field.getColumnCount()];
		for (int r = 0; r < field.getRowCount(); r++) {
			for (int c = 0; c < field.getColumnCount(); c++) {
				final Cell cell = field.getCell(r, c);
				final AbstractCellView cellView = buildCellView(cell, r, c);
				this.cellViews[r][c] = cellView;
				cellView.setOnMouseClicked(event -> {
					onCellClicked(cellView.getRow(), cellView.getColumn(), event);
				});
			}
		}
	}

	protected abstract AbstractCellView buildCellView(final Cell cell, final int row, final int col);

	private void onCellClicked(final int row, final int column, final MouseEvent event) {
		if (!this.gameStarted) {
			this.gameStarted = true;
			this.listener.gameStarted();
		}
		if (this.gameEnded) {
			return;
		}
		if (event.getButton() == MouseButton.PRIMARY) {
			this.field.reveal(row, column);
			refreshAll();
			if (this.field.isGameLost()) {
				this.gameEnded = true;
				this.listener.gameLost();
			}
		} else if (event.getButton() == MouseButton.SECONDARY) {
			final Cell cell = this.field.getCell(row, column);
			if (cell.isFlagged()) {
				// removing flag
				cell.setFlagged(false);
				this.mineCount.set(this.mineCount.get() + 1);
				this.listener.mineCounterChanged(this.mineCount.get());
			} else {
				// adding flag
				cell.setFlagged(true);
				final int nbRemaining = this.mineCount.get() - 1;
				if (nbRemaining == 0) {
					this.gameEnded = true;
				}
				this.mineCount.set(nbRemaining);
				this.listener.mineCounterChanged(nbRemaining);
			}
			this.cellViews[row][column].refresh();
		}
	}

	private void refreshAll() {
		// TODO Optimiser en mémorisant au niveau de l'AbstractMineField les cellules
		// modifiées
		for (int r = 0; r < this.field.getRowCount(); r++) {
			for (int c = 0; c < this.field.getColumnCount(); c++) {
				this.cellViews[r][c].refresh();
			}
		}
	}
}
