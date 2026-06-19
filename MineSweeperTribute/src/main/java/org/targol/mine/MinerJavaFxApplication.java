package org.targol.mine;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.targol.mine.i18n.Messages;
import org.targol.mine.utils.ThemesManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MinerJavaFxApplication extends Application {

	private ConfigurableApplicationContext context;
	private Stage mainWindow;
	final ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault()); //$NON-NLS-1$

	@Override
	public void init() {
		this.context = new SpringApplicationBuilder(MineSweeperTributeApplication.class).headless(false).run();
	}

	@Override
	public void start(final Stage stage) throws Exception {
		this.mainWindow = stage;
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainWindow.fxml"), this.bundle); //$NON-NLS-1$
		loader.setControllerFactory(this.context::getBean);
		final Parent root = loader.load();
		stage.setTitle(Messages.getString("MainWindow.title")); //$NON-NLS-1$
		stage.setScene(new Scene(root));
		ThemesManager.getInstance().setTheme(root.getScene());
		stage.centerOnScreen();
		stage.show();
	}

	@Override
	public void stop() {
		this.context.close();
		Platform.exit();
	}

	public static void main(final String[] args) {
		launch(args);
	}
}
