package org.swiss.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//@Entity
//@Proxy(lazy = false)
//@Table(name = "Round")
public class Round extends AbstractEntity {

	// @Column(name = "number", nullable = false)
	private int number;

	private String tournamentName;

	private List<Player> players;

	// @OneToMany(mappedBy = "tournament")
	private Collection<Match> matches;

	private boolean partial = false;

	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public String getTournamentName() {
		return this.tournamentName;
	}

	public void setTournamentName(final String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public Collection<Match> getMatches() {
		if (this.matches == null) {
			this.matches = new HashSet<>();
		}
		return this.matches;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(final List<Player> players) {
		this.players = Collections.unmodifiableList(players);
	}

	public boolean isPartial() {
		return this.partial;
	}

	public void setPartial(final boolean partial) {
		this.partial = partial;
	}

}
