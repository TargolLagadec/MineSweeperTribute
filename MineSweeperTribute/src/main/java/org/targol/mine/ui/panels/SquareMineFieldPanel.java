package org.targol.mine.ui.panels;

import org.targol.mine.game.Cell;
import org.targol.mine.game.IMineField;
import org.targol.mine.game.IMineFieldListener;
import org.targol.mine.ui.components.AbstractCellView;
import org.targol.mine.ui.components.SquareCellView;

import javafx.scene.layout.GridPane;

public class SquareMineFieldPanel extends AbstractMineFieldPanel {

	private final GridPane grid;

	public SquareMineFieldPanel(final IMineField field, final IMineFieldListener listener) {
		this.grid = new GridPane();
		super(field, listener);
		getChildren().add(this.grid);
	}

	@Override
	protected AbstractCellView buildCellView(final Cell cell, final int r, final int c) {
		final SquareCellView view = new SquareCellView(cell, r, c);
		this.grid.add(view, c, r);
		return view;
	}

}
