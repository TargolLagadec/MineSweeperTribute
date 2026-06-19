package org.targol.mine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;

@SpringBootApplication
public class MineSweeperTributeApplication {

	public static String APP_VERSION;

	@Value("${spring.application.version:1.0}")
	public void setAppVersion(String version) {
		System.err.println(version);
		MineSweeperTributeApplication.APP_VERSION = version;
	}

	public static void main(final String[] args) {
		Application.launch(MinerJavaFxApplication.class, args);
	}

}
