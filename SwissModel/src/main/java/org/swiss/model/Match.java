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

	private int roundNumber;

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

	public int getRoundNumber() {
		return this.roundNumber;
	}

	public void setRoundNumber(final int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public boolean isFinished() {
		return this.score1 > 0 || this.score2 > 0;
	}

	public IPlayerScore getWinner() {
		return this.score1 > this.score2 ? this.player1 : this.player2;
	}

	public IPlayerScore getLooser() {
		final IPlayerScore winner = this.getWinner();
		return this.player1.getName().equals(winner.getName()) ? this.player2 : this.player1;
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
