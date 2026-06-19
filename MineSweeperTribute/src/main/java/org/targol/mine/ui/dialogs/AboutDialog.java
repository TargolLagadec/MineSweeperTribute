package org.targol.mine.ui.dialogs;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.targol.mine.MineSweeperTributeApplication;
import org.targol.mine.i18n.Messages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Window;

public class AboutDialog extends Dialog<Void> {

	@FXML
	private ButtonType okButtonType;
	@FXML
	private Label appTitle;

	private final ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault()); //$NON-NLS-1$

	public AboutDialog(final Window owner) {
		try {

			final FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/dialogs/AboutDialog.fxml"), //$NON-NLS-1$
					this.bundle);
			loader.setController(this);
			initOwner(owner);
			initModality(Modality.APPLICATION_MODAL);
			setResizable(false);
			setTitle(Messages.getString("MainWindow.mnu_game.itm_about")); //$NON-NLS-1$
			final DialogPane dialogPane = loader.load();
			setDialogPane(dialogPane);
			this.appTitle.setText(Messages.getString("AboutDialog.text", MineSweeperTributeApplication.APP_VERSION)); //$NON-NLS-1$
			final Button okButton = (Button) dialogPane.lookupButton(this.okButtonType);
			okButton.addEventFilter(ActionEvent.ACTION, this::onBtnOkClick);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	private void onBtnOkClick(final ActionEvent event) {
	}

}