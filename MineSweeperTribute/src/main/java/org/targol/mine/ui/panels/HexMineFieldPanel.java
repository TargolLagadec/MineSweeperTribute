package org.targol.mine.ui.panels;

import org.targol.mine.game.AbstractMineField;
import org.targol.mine.game.Cell;
import org.targol.mine.game.IMineFieldListener;
import org.targol.mine.ui.components.AbstractCellView;
import org.targol.mine.ui.components.HexCellView;

public class HexMineFieldPanel extends AbstractMineFieldPanel {

	public HexMineFieldPanel(final AbstractMineField field, final IMineFieldListener listener) {
		super(field, listener);
	}

	@Override
	protected AbstractCellView buildCellView(final Cell cell, final int r, final int c) {
		final HexCellView cellView = new HexCellView(cell, r, c);
		if (r == 0 || r % 2 == 0) {
			cellView.relocate(15 + 30 * c, r * 10);
		} else {
			cellView.relocate(30 * c + 30, r * 10);
		}
		getChildren().add(cellView);
		return cellView;
	}
}
