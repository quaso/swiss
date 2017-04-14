package org.swiss.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.swiss.model.Player;
import org.swiss.repository.PlayerRepository;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

	private final Map<String, Player> map = new HashMap<>();

	@Override
	public Optional<Player> findByNameAndTournamentName(final String name, final String tournamentName) {
		final String key = this.getKey(name, tournamentName);
		if (this.map.containsKey(key)) {
			return Optional.of(this.map.get(key));
		}
		return Optional.empty();
	}

	@Override
	public Player save(final Player player) {
		if (this.map.containsKey(player.getName())) {
			throw new IllegalStateException("Player with name [" + player.getName() + "] exists already");
		}
		this.map.put(this.getKey(player.getName(), player.getTournamentName()), player);
		return player;
	}

	@Override
	public void delete(final Iterable<Player> players) {
		players.forEach(p -> this.map.entrySet().removeIf(e -> e.getKey().equals(p.getName())));
	}

	private String getKey(final String name, final String tournamentName) {
		return tournamentName + "_" + name;
	}

}
