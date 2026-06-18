package org.targol.mine.ui.components;

import org.targol.mine.game.Cell;
import org.targol.mine.game.enums.CellDisplayState;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class HexCellView extends StackPane {

	public static final int CELL_HEIGHT_AND_WIDTH = 21;
	private final Cell cell;
	private final int row;
	private final int column;
	private String currentCssClass;
	private final Label label = new Label();
	private final Canvas backGround = new Canvas(21, 21);
	private CellDisplayState previousDisplay;

	public HexCellView(final Cell cell, final int row, final int column) {
		super();
		this.cell = cell;
		this.row = row;
		this.column = column;
		refresh();
		fillBackGround();
		getChildren().add(this.backGround);
		getChildren().add(this.label);
		this.label.getStyleClass().clear();
		this.label.getStyleClass().add("cell-label");
		setMinSize(CELL_HEIGHT_AND_WIDTH, CELL_HEIGHT_AND_WIDTH);
		setPrefSize(CELL_HEIGHT_AND_WIDTH, CELL_HEIGHT_AND_WIDTH);
		setMaxSize(CELL_HEIGHT_AND_WIDTH, CELL_HEIGHT_AND_WIDTH);
//		backgroundProperty().addListener((obs, oldBg, newBg) -> {
//			cleanBackgroundAndBorder();
//		});
	}

	@Override
	protected void layoutChildren() {
		refresh();
		super.layoutChildren();
	}

	private void fillBackGround() {
		GraphicsContext gc = this.backGround.getGraphicsContext2D();
		// Background
		gc.setFill(getBgColorFromCss());
		gc.fillPolygon(new double[] { 05, 15, 20, 15, 05, 00 }, new double[] { 00, 00, 10, 20, 20, 10 }, 6);
		// Border
		gc.setLineWidth(1);
		gc.setStroke(Color.BLACK);
		gc.strokePolygon(new double[] { 05, 15, 20, 15, 05, 00 }, new double[] { 00, 00, 10, 20, 20, 10 }, 6);
	}

	private void cleanBackgroundAndBorder() {
		refresh();
		setBackground(Background.fill(Color.TRANSPARENT));
		setBorder(Border.EMPTY);
		super.layoutChildren();
	}

	private Color getBgColorFromCss() {
		Background background = getBackground();

		if (background != null && !background.getFills().isEmpty()) {
			Paint paint = background.getFills().getFirst().getFill();

			if (paint instanceof Color color) {
				return color;
			}
		}
		return Color.GRAY;
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
		if (state.equals(this.previousDisplay) && Border.EMPTY.equals(getBorder())) {
			return;
		}
		System.out.println("update style to " + state.getCssClass());
		this.previousDisplay = state;
		if (this.currentCssClass != null) {
			getStyleClass().remove(this.currentCssClass);
		}
		this.currentCssClass = state.getCssClass();
		getStyleClass().add(this.currentCssClass);
		this.label.setText(state.getLabel());
		fillBackGround();
		setBackground(Background.fill(Color.TRANSPARENT));
		setBorder(Border.EMPTY);
	}
}
