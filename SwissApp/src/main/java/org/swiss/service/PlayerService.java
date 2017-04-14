package org.swiss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiss.model.Player;
import org.swiss.repository.PlayerRepository;

@Service
// @Transactional
public class PlayerService {
	@Autowired
	private PlayerRepository playerRepository;

	public Player find(final String name, final String tournamentName) {
		return this.playerRepository.findByNameAndTournamentName(name, tournamentName)
				.orElseThrow(() -> new IllegalStateException("player [" + name + "] cannot be found"));
	}

	public Player save(final Player player) {
		return this.playerRepository.save(player);
	}

	public void delete(final Iterable<Player> players) {
		this.playerRepository.delete(players);

	}
}
