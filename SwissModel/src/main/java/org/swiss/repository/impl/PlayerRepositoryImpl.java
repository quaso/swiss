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
	public Optional<Player> findByName(final String name) {
		if (this.map.containsKey(name)) {
			return Optional.of(this.map.get(name));
		}
		return Optional.empty();
	}

	@Override
	public Player save(final Player player) {
		if (this.map.containsKey(player.getName())) {
			throw new IllegalStateException("Player with name [" + player.getName() + "] exists already");
		}
		this.map.put(player.getName(), player);
		return player;
	}

}
