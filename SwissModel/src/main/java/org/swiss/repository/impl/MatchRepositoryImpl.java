package org.swiss.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.swiss.model.Match;
import org.swiss.repository.MatchRepository;

@Repository
public class MatchRepositoryImpl implements MatchRepository {

	private final Map<String, Match> map = new HashMap<>();

	@Override
	public Optional<Match> findMatchByPlayer1IdAndPlayer2Id(final String player1Id, final String player2Id) {
		final String key = this.createKey(player1Id, player2Id);
		if (this.map.containsKey(key)) {
			return Optional.of(this.map.get(key));
		}
		return Optional.empty();
	}

	@Override
	public void save(final Match match) {
		this.map.put(this.createKey(match.getPlayer1().getId(), match.getPlayer2().getId()), match);
	}

	@Override
	public void deleteByTournamentName(final String name) {
		this.map.entrySet().removeIf(e -> e.getValue().getTournament().getName().equals(name));
	}

	private String createKey(final String player1Id, final String player2Id) {
		return player1Id + "_vs_" + player2Id;
	}

}
