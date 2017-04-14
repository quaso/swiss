package org.swiss.repository;

import java.util.Optional;

import org.swiss.model.Player;

//@Transactional
public interface PlayerRepository {// extends CrudRepository<Player, String> {
	public Optional<Player> findByNameAndTournamentName(String name, String tournamentName);

	public Player save(Player player);

	public void delete(Iterable<Player> players);
}
