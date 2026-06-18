package org.targol.mine.ui.dialogs;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import org.targol.mine.game.HexMineField;
import org.targol.mine.game.IMineField;
import org.targol.mine.game.SquareMineField;
import org.targol.mine.game.enums.Difficulty;
import org.targol.mine.game.enums.GameType;
import org.targol.mine.i18n.Messages;
import org.targol.mine.ui.components.LocalizedEnumSelector;
import org.targol.mine.ui.utils.GuiUtils;
import org.targol.mine.utils.PreferencesManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Window;

public class NewGameDialog extends Dialog<IMineField> {

	@FXML
	private LocalizedEnumSelector<GameType> gameType;
	@FXML
	private TextField nameTextField;
	@FXML
	private LocalizedEnumSelector<Difficulty> difficulty;
	@FXML
	private Slider nbCols;
	@FXML
	private Slider nbRows;
	@FXML
	private ButtonType okButtonType;
	final Window owner;
	private final ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault()); //$NON-NLS-1$

	public NewGameDialog(final Window owner) {
		this.owner = owner;
		try {

			final FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/dialogs/NewGameDialog.fxml"), //$NON-NLS-1$
					this.bundle);
			loader.setController(this);
			initOwner(owner);
			initModality(Modality.APPLICATION_MODAL);
			setResizable(false);
			setTitle(Messages.getString("Game.new")); //$NON-NLS-1$
			final DialogPane dialogPane = loader.load();
			this.gameType.setEnumClass(GameType.class);
			this.gameType.selectedValueProperty().addListener((observable, oldValue, newValue) -> {
				setMaxDimensions();
			});
			this.difficulty.setEnumClass(Difficulty.class);
			final PreferencesManager pref = PreferencesManager.getInstance();
			this.gameType.setSelectedValue(pref.getLastChoosenGameType());
			this.difficulty.setSelectedValue(pref.getLastChoosenDifficulty());
			setDialogPane(dialogPane);
			final Button okButton = (Button) dialogPane.lookupButton(this.okButtonType);
			okButton.addEventFilter(ActionEvent.ACTION, this::onBtnOkClick);
			setResultConverter(buttonType -> {
				if (!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
					return null;
				}
				if (GameType.SQUARE.equals(this.gameType.getSelectedValue())) {
					return new SquareMineField((int) this.nbRows.getValue(), (int) this.nbCols.getValue(),
							this.difficulty.getSelectedValue());
				}
				return new HexMineField((int) this.nbRows.getValue(), (int) this.nbCols.getValue(),
						this.difficulty.getSelectedValue());
			});
			setOnShowing(dialogEvent -> Platform.runLater(() -> this.gameType.requestFocus()));
			setMaxDimensions();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void setMaxDimensions() {
		final PreferencesManager pref = PreferencesManager.getInstance();
		this.nbCols.setMax(200);
		this.nbCols.setMajorTickUnit(10);
		this.nbCols.setShowTickMarks(true);
		this.nbCols.setShowTickLabels(true);
		final int lastChoosenCols = pref.getIntPreference(PreferencesManager.PREF_LAST_COLNUM);
		if (lastChoosenCols >= this.nbCols.getMin() && lastChoosenCols <= this.nbCols.getMax()) {
			this.nbCols.setValue(lastChoosenCols);
		}
		this.nbRows.setMax(100);
		this.nbRows.setMajorTickUnit(10);
		this.nbRows.setShowTickMarks(true);
		this.nbRows.setShowTickLabels(true);
		final int lastChoosenRows = pref.getIntPreference(PreferencesManager.PREF_LAST_ROWNUM);
		if (lastChoosenRows >= this.nbRows.getMin() && lastChoosenRows <= this.nbRows.getMax()) {
			this.nbRows.setValue(lastChoosenRows);
		}
	}

	@FXML
	private void onBtnOkClick(final ActionEvent event) {
		if (this.gameType.getSelectedValue() == null) {
			GuiUtils.errorAlert(Messages.getString("NewGameDialog.error.gameType")); //$NON-NLS-1$
			event.consume();
			return;
		}
		if (this.difficulty.getSelectedValue() == null) {
			GuiUtils.errorAlert(Messages.getString("NewGameDialog.error.difficulty")); //$NON-NLS-1$
			event.consume();
			return;
		}
		final PreferencesManager pref = PreferencesManager.getInstance();
		pref.setIntPreference(PreferencesManager.PREF_LAST_COLNUM, (int) this.nbCols.getValue());
		pref.setIntPreference(PreferencesManager.PREF_LAST_ROWNUM, (int) this.nbRows.getValue());
		pref.setLastChoosenDifficulty(this.difficulty.getSelectedValue());
		pref.setLastChoosenGameType(this.gameType.getSelectedValue());
	}
}
