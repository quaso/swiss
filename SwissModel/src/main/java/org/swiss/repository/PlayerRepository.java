package org.swiss.repository;

import java.util.Optional;

import org.swiss.model.Player;

//@Transactional
public interface PlayerRepository {// extends CrudRepository<Player, String> {
	public Optional<Player> findByName(String name);

	public Player save(Player player);
}
