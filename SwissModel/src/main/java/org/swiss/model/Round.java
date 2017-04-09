package org.swiss.model;

import java.util.ArrayList;
import java.util.Collections;
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
	private List<Match> matches;

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

	public List<Match> getMatches() {
		if (this.matches == null) {
			this.matches = new ArrayList<>();
		}
		return this.matches;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(final List<Player> players) {
		this.players = Collections.unmodifiableList(players);
	}

}
