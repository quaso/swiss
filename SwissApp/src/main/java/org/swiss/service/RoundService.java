package org.swiss.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiss.model.Player;
import org.swiss.model.Round;
import org.swiss.model.Tournament;
import org.swiss.repository.RoundRepository;
import org.swiss.util.PlayerComparator;

@Service
// @Transactional
public class RoundService {

	@Autowired
	private RoundRepository roundRepository;

	@Autowired
	private MatchService matchService;

	@Autowired
	private PlayerComparator playerComparator;

	public Round addNewRound(final Tournament tournament) {
		final Round round = new Round();
		round.setTournamentName(tournament.getName());
		round.setNumber(this.roundRepository.countByTournamentName(tournament.getName()));

		if (round.getNumber() == 0) {
			round.setPlayers(tournament.getPlayers());
		} else {
			round.setPlayers(this.calculateOrder(tournament, round.getNumber()));
		}

		final List<Player> players = new ArrayList<>(round.getPlayers());
		while (!players.isEmpty()) {
			final Player player1 = players.remove(0);
			int i = 0;
			while (this.matchService.findMatch(player1, players.get(i)).isPresent()) {
				i++;
				if (players.size() == i) {
					throw new IllegalStateException("Cannot find opponent for " + player1.getName());
				}
			}
			final Player player2 = players.remove(i);
			round.getMatches().add(this.matchService.createMatch(tournament, player1, player2, round.getNumber()));
		}

		this.roundRepository.save(round);

		return round;
	}

	private List<Player> calculateOrder(final Tournament tournament, final int roundNumber) {
		final Round round = this.roundRepository.getRoundByNumber(tournament.getName(), roundNumber);
		final List<Player> players = new ArrayList<>(round.getPlayers());
		this.playerComparator.setPreviousOrder(round.getPlayers());
		players.sort(this.playerComparator);
		return players;
	}

	public void deleteForTournament(final Tournament tournament) {
		this.roundRepository.deleteByTournamentName(tournament.getName());
	}
}
