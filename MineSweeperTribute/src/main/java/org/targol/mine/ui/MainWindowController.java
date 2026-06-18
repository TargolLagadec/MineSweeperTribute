package org.targol.mine.ui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;
import org.targol.mine.game.IMineField;
import org.targol.mine.game.IMineFieldListener;
import org.targol.mine.game.ScreenLimits;
import org.targol.mine.game.SquareMineField;
import org.targol.mine.i18n.Messages;
import org.targol.mine.ui.components.CellView;
import org.targol.mine.ui.components.HexCellView;
import org.targol.mine.ui.dialogs.GameResultDialog;
import org.targol.mine.ui.dialogs.NewGameDialog;
import org.targol.mine.ui.dialogs.PreferencesDialog;
import org.targol.mine.ui.panels.HexMineFieldPanel;
import org.targol.mine.ui.panels.SquareMineFieldPanel;
import org.targol.mine.ui.panels.TestPanel;
import org.targol.mine.ui.panels.WelcomePanelController;
import org.targol.mine.ui.utils.GuiUtils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;
import javafx.util.Duration;

@Controller
public class MainWindowController implements IMineFieldListener {

	@FXML
	private MenuItem mnuClose;
	@FXML
	private StackPane contentPane;
	@FXML
	Label nbMinesLeft;
	@FXML
	Label timeElapsed;
	int initialNbMines = 0;

	private final IntegerProperty nbSeconds = new SimpleIntegerProperty();
	private Timeline timer;

	private ScreenLimits maxSquareMineFieldSize;
	private ScreenLimits maxHexMineFieldSize;
	private boolean isSquare;
	private final ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault()); //$NON-NLS-1$

	public MainWindowController() {
	}

	@FXML
	private void initialize() {
		loadWelcomeView();
		// loadTestView();
	}

	@FXML
	private void newGame() {
		this.maxSquareMineFieldSize = GuiUtils.getMaxSquaresMineFieldRowsAndColForScreen(this.contentPane);
		this.maxHexMineFieldSize = GuiUtils.getMaxHexMineFieldRowsAndColForScreen(this.contentPane);
		final NewGameDialog dialog = new NewGameDialog(this.contentPane.getScene().getWindow(),
				this.maxSquareMineFieldSize, this.maxHexMineFieldSize);
		dialog.showAndWait().ifPresent(mineField -> {
//			System.out.println(mineField);
			displayMineField(mineField);
		});

	}

	@FXML
	private void showPrefs() {
		final PreferencesDialog dialog = new PreferencesDialog(this.contentPane.getScene().getWindow());
		dialog.showAndWait();
	}

	@FXML
	private void quit() {
		Platform.exit();
	}

	private void displayMineField(final IMineField field) {
		if (field instanceof SquareMineField) {
			this.isSquare = true;
		} else {
			this.isSquare = false;
		}

		this.nbSeconds.set(0);
		this.timeElapsed.textProperty().bind(this.nbSeconds.asString("%03d")); //$NON-NLS-1$
		this.initialNbMines = field.getNbMines();
		mineCounterChanged(field.getNbMines());

		Pane boardPane;
		final Window win = this.contentPane.getScene().getWindow();
		if (this.isSquare) {
			win.setWidth(field.getColumnCount() * CellView.CELL_HEIGHT_AND_WIDTH);
			win.setHeight(
					field.getRowCount() * CellView.CELL_HEIGHT_AND_WIDTH + this.maxSquareMineFieldSize.headerHeight());
			boardPane = new SquareMineFieldPanel(field, this);
		} else {
			win.setWidth(field.getColumnCount() * HexCellView.CELL_HEIGHT_AND_WIDTH);
			win.setHeight(field.getRowCount() * HexCellView.CELL_HEIGHT_AND_WIDTH / 2
					+ HexCellView.CELL_HEIGHT_AND_WIDTH + this.maxHexMineFieldSize.headerHeight());
			boardPane = new HexMineFieldPanel(field, this);
		}
		win.centerOnScreen();
		this.contentPane.getChildren().clear();
		this.contentPane.getChildren().add(boardPane);
	}

	@Override
	public void mineCounterChanged(final int remainingMines) {
		this.nbMinesLeft.setText(Integer.toString(remainingMines));
		if (remainingMines == 0) {
			gameWon();
		}
	}

	@Override
	public void gameStarted() {
		if (this.timer != null) {
			this.timer.stop();
		}
		this.timer = new Timeline(
				new KeyFrame(Duration.seconds(1), event -> this.nbSeconds.set(this.nbSeconds.get() + 1)));
		this.timer.setCycleCount(Animation.INDEFINITE);
		this.timer.play();
	}

	private void gameWon() {
		if (this.timer != null) {
			this.timer.stop();
		}
		final String result = Messages.getString("General.gameWon", this.initialNbMines, this.nbSeconds.getValue()); //$NON-NLS-1$
		final GameResultDialog dialog = new GameResultDialog(this.contentPane.getScene().getWindow(), result, true);
		dialog.showAndWait();
		// loadWelcomeView();
	}

	@Override
	public void gameLost() {
		if (this.timer != null) {
			this.timer.stop();
		}
		final int nbMissed = Integer.parseInt(this.nbMinesLeft.getText());
		final int nbDiscover = this.initialNbMines - nbMissed;
		final String result = Messages.getString("General.gameLost", nbDiscover, this.nbSeconds.getValue()); //$NON-NLS-1$
		final GameResultDialog dialog = new GameResultDialog(this.contentPane.getScene().getWindow(), result, false);
		dialog.showAndWait();
		// loadWelcomeView();
	}

	private void loadWelcomeView() {
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/panels/WelcomePanel.fxml"), //$NON-NLS-1$
					this.bundle);
			final Parent root = loader.load();
			this.contentPane.getChildren().setAll(root);
			final WelcomePanelController controller = loader.getController();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadTestView() {
		final TestPanel boardPane = new TestPanel();
		this.contentPane.getChildren().clear();
		this.contentPane.getChildren().add(boardPane);
	}
}