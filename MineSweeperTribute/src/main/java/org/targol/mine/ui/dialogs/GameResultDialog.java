package org.targol.mine.ui.dialogs;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.targol.mine.ui.utils.GuiUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Window;

public class GameResultDialog extends Dialog<Void> {

	@FXML
	private ImageView imageView;
	@FXML
	private Label resultTextField;
	@FXML
	private ButtonType okButtonType;
	final Window owner;

	private final ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault()); //$NON-NLS-1$

	public GameResultDialog(final Window owner, final String result, final boolean hasWon) {
		this.owner = owner;
		try {

			final FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/dialogs/GameResultDialog.fxml"), //$NON-NLS-1$
					this.bundle);
			loader.setController(this);
			initOwner(owner);
			initModality(Modality.APPLICATION_MODAL);
			setResizable(false);
			final DialogPane dialogPane = loader.load();
			setDialogPane(dialogPane);
			this.resultTextField.setText(result);
			if (hasWon) {
				this.imageView.setImage(GuiUtils.createImageViewFromInternalPng("/images/won.png")); //$NON-NLS-1$
			} else {
				this.imageView.setImage(GuiUtils.createImageViewFromInternalPng("/images/lost.png")); //$NON-NLS-1$
			}
			final Button okButton = (Button) dialogPane.lookupButton(this.okButtonType);
			okButton.addEventFilter(ActionEvent.ACTION, this::onBtnOkClick);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	private void initialize() {
	}

	@FXML
	private void onBtnOkClick(final ActionEvent event) {
	}
}
