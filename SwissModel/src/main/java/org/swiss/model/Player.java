package org.swiss.model;

//@Entity
//@Proxy(lazy = false)
//@Table(name = "Player")
public class Player extends AbstractEntity implements IPlayerScore {

	// @Column(name = "name", nullable = true, length = 255, unique=true)
	private String name;

	private int points = 0;
	private int score = 0;

	private String tournamentName;

	public Player() {
	}

	public Player(final String name, final String tournamentName) {
		this();
		this.name = name;
		this.tournamentName = tournamentName;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

	public void setPoints(final int points) {
		this.points = points;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	public void setScore(final int score) {
		this.score = score;
	}

	@Override
	public String getTournamentName() {
		return this.tournamentName;
	}

	@Override
	public String toString() {
		return this.toStringExtended();
	}

}
