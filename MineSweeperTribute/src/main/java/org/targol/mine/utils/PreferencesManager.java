package org.targol.mine.utils;

import java.util.prefs.Preferences;

import org.targol.mine.game.enums.Difficulty;
import org.targol.mine.game.enums.GameType;
import org.targol.mine.utils.ThemesManager.Theme;

public class PreferencesManager {

	private static final String PREF_THEME = "general.theme"; //$NON-NLS-1$
	private static final String PREF_DIFFICULTY = "general.difficulty"; //$NON-NLS-1$
	private static final String PREF_GAME_TYPE = "general.gameType"; //$NON-NLS-1$
	public static final String PREF_LAST_COLNUM = "general.colNum"; //$NON-NLS-1$
	public static final String PREF_LAST_ROWNUM = "general.rowNum"; //$NON-NLS-1$

	private static PreferencesManager instance;
	private final Preferences prefs;

	public static PreferencesManager getInstance() {
		if (instance == null) {
			instance = new PreferencesManager();
		}
		return instance;
	}

	private PreferencesManager() {
		this.prefs = Preferences.userNodeForPackage(PreferencesManager.class);
		initPrefsIfEmpty();
	}

	private void initPrefsIfEmpty() {
		final Theme theme = getCurrentTheme();
		if (theme == null) {
			setCurrentTheme(Theme.GREY);
		}
	}

	public Theme getCurrentTheme() {
		final String theme = this.prefs.get(PREF_THEME, null);
		if (theme != null) {
			return Theme.valueOf(theme);
		}
		return Theme.GREY;
	}

	public void setCurrentTheme(final Theme theme) {
		this.prefs.put(PREF_THEME, theme.toString());
	}

	public Difficulty getLastChoosenDifficulty() {
		final String diff = this.prefs.get(PREF_DIFFICULTY, null);
		if (diff != null) {
			return Difficulty.valueOf(diff);
		}
		return null;
	}

	public void setLastChoosenDifficulty(final Difficulty diff) {
		this.prefs.put(PREF_DIFFICULTY, diff.toString());
	}

	public GameType getLastChoosenGameType() {
		final String gameType = this.prefs.get(PREF_GAME_TYPE, null);
		if (gameType != null) {
			return GameType.valueOf(gameType);
		}
		return GameType.SQUARE;
	}

	public void setLastChoosenGameType(final GameType gameType) {
		this.prefs.put(PREF_GAME_TYPE, gameType.toString());
	}

	public String getPreference(final String prefKey) {
		return this.prefs.get(prefKey, null);
	}

	public void setPreference(final String prefKey, final String value) {
		this.prefs.put(prefKey, value);
	}

	public int getIntPreference(final String prefKey) {
		return this.prefs.getInt(prefKey, 0);
	}

	public void setIntPreference(final String prefKey, final int value) {
		this.prefs.putInt(prefKey, value);
	}

	public boolean getBoolPreference(final String prefKey) {
		return this.prefs.getBoolean(prefKey, false);
	}

	public void setBoolPreference(final String prefKey, final boolean value) {
		this.prefs.putBoolean(prefKey, value);
	}

}
