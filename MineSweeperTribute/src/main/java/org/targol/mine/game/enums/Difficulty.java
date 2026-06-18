package org.targol.mine.game.enums;

import org.targol.mine.i18n.Messages;

public enum Difficulty implements IEnumWithLocalizedLabel {
	VERY_EASY("Difficulty.veryeasy", 11), //$NON-NLS-1$
	EASY("Difficulty.easy", 10), //$NON-NLS-1$
	MEDIUM("Difficulty.medium", 9), //$NON-NLS-1$
	HARD("Difficulty.hard", 8), //$NON-NLS-1$
	HARDER("Difficulty.harder", 7); //$NON-NLS-1$

	private final String i18nId;
	private final int minesFactor;

	private Difficulty(final String i18nId, final int minesFactor) {
		this.i18nId = i18nId;
		this.minesFactor = minesFactor;
	}

	public int getMinesFactor() {
		return this.minesFactor;
	}

	@Override
	public String getLocalizedLabel() {
		return Messages.getString(this.i18nId);
	}
}
