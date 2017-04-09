package org.swiss.model;

//@Entity
//@Proxy(lazy = false)
//@Table(name = "Match")
public class Match extends AbstractEntity {

	private Tournament tournament;

	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "PLAYER1_ID")
	private Player player1;

	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "PLAYER2_ID")
	private Player player2;

	private int score1;

	private int score2;

	private int subScore1;

	private int subScore2;

	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(final Tournament tournament) {
		this.tournament = tournament;
	}

	public Player getPlayer1() {
		return this.player1;
	}

	public void setPlayer1(final Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return this.player2;
	}

	public void setPlayer2(final Player player2) {
		this.player2 = player2;
	}

	public int getScore1() {
		return this.score1;
	}

	public void setScore1(final int score1) {
		this.score1 = score1;
	}

	public int getScore2() {
		return this.score2;
	}

	public void setScore2(final int score2) {
		this.score2 = score2;
	}

	public int getSubScore1() {
		return this.subScore1;
	}

	public void setSubScore1(final int subScore1) {
		this.subScore1 = subScore1;
	}

	public int getSubScore2() {
		return this.subScore2;
	}

	public void setSubScore2(final int subScore2) {
		this.subScore2 = subScore2;
	}

	@Override
	public String toString() {
		String score = "";
		if (this.score1 != 0 || this.score2 != 0) {
			score = String.format(" = %d:%d", this.score1, this.score2);
		}
		return "Match [" + this.player1 + " vs " + this.player2 + score + "]";
	}

}