package org.targol.mine.ui.panels;

import java.util.Random;

import org.targol.mine.game.Cell;
import org.targol.mine.ui.components.HexCellView;

import javafx.scene.layout.Pane;

public class TestPanel extends Pane {

	public TestPanel() {
		final Random rnd = new Random();
		for (int row = 0; row < 30; row++) {
			for (int col = 0; col < 30; col++) {
				Cell cell;
				if (col == 0 && row < 12) {
					cell = createTestCell(row, row, col);
				} else {
					cell = createTestCell(rnd.nextInt(12), row, col);
				}
				final HexCellView cellView = new HexCellView(cell, row, col);
				if (row == 0 || row % 2 == 0) {
					cellView.relocate(30 * col, row * 10);
				} else {
					cellView.relocate(30 * col + 15, row * 10);
				}
				getChildren().add(cellView);
			}
		}
	}

	private Cell createTestCell(final int nb, final int row, final int col) {
		final Cell cell = new Cell(row, col);
		if (nb >= 1 && nb <= 8) {
			cell.setRevealed(true);
			for (int i = 0; i < nb; i++) {
				cell.incrementAdjacentMineCount();
			}
		} else if (nb == 9) {
			cell.setFlagged(true);
		} else if (nb == 10) {
			cell.setRevealed(true);
			cell.setMine(true);
		}
		return cell;
	}

}