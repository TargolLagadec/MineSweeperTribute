package org.targol.mine.ui.components;

import org.targol.mine.game.enums.IEnumWithLocalizedLabel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class LocalizedEnumSelector<T extends Enum<T> & IEnumWithLocalizedLabel> extends VBox {

	private Class<T> enumClass;
	private ToggleGroup toggleGroup;
	private final ObjectProperty<T> selectedValue = new SimpleObjectProperty<>();

	public void setEnumClass(final Class<T> enumClass) {
		this.enumClass = enumClass;
		this.toggleGroup = new ToggleGroup();
		this.buildView();
		this.toggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
			if (equalValue(oldToggle, newToggle)) {
				return;
			}
			if (newToggle == null) {
				this.selectedValue.set(null);
			} else {
				this.selectedValue.set(this.enumClass.cast(newToggle.getUserData()));
			}
		});
	}

	private boolean equalValue(Toggle oldToggle, Toggle newToggle) {
		T oldValue = oldToggle == null ? null : (T) oldToggle.getUserData();
		T newValue = newToggle == null ? null : (T) newToggle.getUserData();
		if (newValue != null && newValue.equals(oldValue)) {
			return true;
		}
		if (oldValue != null && oldValue.equals(newValue)) {
			return true;
		}
		if (newValue == null && oldValue == null) {
			return true;
		}
		return false;
	}

	private void buildView() {
		for (final T value : this.enumClass.getEnumConstants()) {
			final RadioButton radioButton = new RadioButton(value.getLocalizedLabel());
			radioButton.setUserData(value);
			radioButton.setToggleGroup(this.toggleGroup);
			getChildren().add(radioButton);
		}
	}

	public T getSelectedValue() {
		return this.selectedValue.get();
	}

	public void setSelectedValue(final T value) {
		this.selectedValue.set(value);
		if (value == null) {
			this.toggleGroup.selectToggle(null);
			return;
		}

		for (final Toggle toggle : this.toggleGroup.getToggles()) {
			if (value.equals(toggle.getUserData())) {
				this.toggleGroup.selectToggle(toggle);
				return;
			}
		}
	}

	public ObjectProperty<T> selectedValueProperty() {
		return this.selectedValue;
	}
}
