package org.swiss.model;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@Proxy(lazy = false)
//@Table(name = "Tournament")
public class Tournament extends AbstractEntity {

	// @Column(name = "name", nullable = false)
	private String name;

	private int maxScorePerRound;

	// @OneToMany(mappedBy = "tournament")
	private List<Player> players;

	private List<Round> rounds;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getMaxScorePerRound() {
		return this.maxScorePerRound;
	}

	public void setMaxScorePerRound(final int maxScorePerRound) {
		this.maxScorePerRound = maxScorePerRound;
	}

	public List<Player> getPlayers() {
		if (this.players == null) {
			this.players = new ArrayList<>();
		}
		return this.players;
	}

	public List<Round> getRounds() {
		if (this.rounds == null) {
			this.rounds = new ArrayList<>();
		}
		return this.rounds;
	}

}
