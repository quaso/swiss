package org.swiss.model;

//@Entity
//@Proxy(lazy = false)
//@Table(name = "Player")
public class Player extends AbstractEntity {

	// @Column(name = "name", nullable = true, length = 255, unique=true)
	private String name;

	private int points = 0;
	private int score = 0;

	// @ManyToOne
	private Tournament tournament;

	public Player() {
	}

	public Player(final String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(final int points) {
		this.points = points;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(final int score) {
		this.score = score;
	}

	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(final Tournament tournament) {
		this.tournament = tournament;
	}

	public String toStringExtended() {
		return "Player [name=" + this.name + ", points=" + this.points + ", score=" + this.score + "]";
	}

	@Override
	public String toString() {
		return "Player [" + this.name + "]";
	}

}
