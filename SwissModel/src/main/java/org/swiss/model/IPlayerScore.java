package org.swiss.model;

public interface IPlayerScore {

	String getName();

	int getPoints();

	int getScore();

	String getTournamentName();

	public default String toStringExtended() {
		return "Player [name=" + this.getName() + ", points=" + this.getPoints() + ", score=" + this.getScore() + "]";
	}

}