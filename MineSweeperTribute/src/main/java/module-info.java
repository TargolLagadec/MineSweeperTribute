module moduleInfo {
	exports org.targol.mine;
	exports org.targol.mine.ui.utils;
	exports org.targol.mine.game;
	exports org.targol.mine.ui.dialogs;
	exports org.targol.mine.game.enums;
	exports org.targol.mine.i18n;
	exports org.targol.mine.ui.panels;
	exports org.targol.mine.ui.components;
	exports org.targol.mine.ui;
	exports org.targol.mine.utils;

	requires java.prefs;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires spring.beans;
	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires spring.core;
}