package org.targol.mine.game.enums;

public enum CellDisplayState {
	HIDDEN("", "cell-hidden"), //$NON-NLS-1$ //$NON-NLS-2$
	EMPTY("", "cell-empty"), //$NON-NLS-1$ //$NON-NLS-2$
	FLAGGED("⚑", "cell-flagged"), //$NON-NLS-1$ //$NON-NLS-2$
	MINE("✹", "cell-mine"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_1("1", "cell-number_1"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_2("2", "cell-number_2"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_3("3", "cell-number_3"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_4("4", "cell-number_4"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_5("5", "cell-number_5"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_6("6", "cell-number_6"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_7("7", "cell-number_7"), //$NON-NLS-1$ //$NON-NLS-2$
	NUMBER_8("8", "cell-number_8"); //$NON-NLS-1$ //$NON-NLS-2$

	private final String label;
	private final String cssClass;

	private CellDisplayState(final String label, final String cssClass) {
		this.label = label;
		this.cssClass = cssClass;
	}

	public String getCssClass() {
		return this.cssClass;
	}

	public String getLabel() {
		return this.label;
	}
}
