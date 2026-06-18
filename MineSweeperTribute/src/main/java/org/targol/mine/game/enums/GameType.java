package org.targol.mine.game.enums;

import org.targol.mine.i18n.Messages;

public enum GameType implements IEnumWithLocalizedLabel {
	SQUARE("GameType.square"), //$NON-NLS-1$
	HEX("GameType.hex"); //$NON-NLS-1$

	private final String i18nId;

	private GameType(final String i18nId) {
		this.i18nId = i18nId;
	}

	@Override
	public String getLocalizedLabel() {
		return Messages.getString(this.i18nId);
	}
}
