package org.targol.mine.game;

public record Position2D(int row, int column) {

	public Position2D(double r, double c) {
		this((int) r, (int) c);
	}

}
