package org.targol.mine.ui.components;

import javafx.scene.paint.Color;

public enum CellDisplayState {
	HIDDEN("", "0xA0A0A0"), //$NON-NLS-1$ //$NON-NLS-2$
	EMPTY("", "0x9cf09c"), //$NON-NLS-1$ //$NON-NLS-2$
	FLAGGED("⚑", "0x7be0ff"), //$NON-NLS-1$ //$NON-NLS-2$
	MINE("✹", "0xff0000"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_1("1", "0xffff00"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_2("2", "0xffdf00"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_3("3", "0xffbf00"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_4("4", "0xff9f00"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_5("5", "0xff7f00"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_6("6", "0xff5f00"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_7("7", "0xff3f00"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_8("8", "0xff1f00"); //$NON-NLS-1$ //$NON-NLS-2$

	private final String label;
	private final String colorCode;

	private CellDisplayState(final String label, final String colorCode) {
		this.label = label;
		this.colorCode = colorCode;
	}

	public Color getBgColor() {
		return Color.web(this.colorCode);
	}

	public String getLabel() {
		return this.label;
	}
}
