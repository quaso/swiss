package org.swiss.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiss.model.Player;
import org.swiss.model.Round;
import org.swiss.model.Tournament;
import org.swiss.repository.TournamentRepository;

@Service
// @Transactional
public class TournamentService {

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private RoundService roundService;

	@Autowired
	private PlayerService playerService;

	public Tournament find(final String name) {
		return this.tournamentRepository.findByName(name)
				.orElseThrow(() -> new IllegalStateException("tournament [" + name + "] not found"));
	}

	public boolean createTournament(final String name, final boolean overwrite) {
		final Optional<Tournament> existingTournament = this.tournamentRepository.findByName(name);
		if (existingTournament.isPresent()) {
			if (overwrite) {
				this.tournamentRepository.delete(existingTournament.get());
			} else {
				return false;
			}
		}
		final Tournament tournament = new Tournament();
		tournament.setName(name);
		this.tournamentRepository.save(tournament);
		return true;
	}

	public List<Player> getPlayers(final String tournamentName) {
		return this.find(tournamentName).getPlayers();
	}

	public void setPlayers(final String tournamentName, final List<String> players) {
		final Tournament tournament = this.find(tournamentName);
		players.forEach(s -> tournament.getPlayers().add(this.playerService.save(new Player(s))));

		this.tournamentRepository.save(tournament);
	}

	public void replacePlayer(final String tournamentName, final String oldName, final String newName) {
		final Tournament tournament = this.find(tournamentName);
		for (final Player p : tournament.getPlayers()) {
			if (oldName.equals(p.getName())) {
				p.setName(newName);
				break;
			}
		}
		this.tournamentRepository.save(tournament);
	}

	public Round addNewRound(final String tournamentName) {
		return this.roundService.addNewRound(this.find(tournamentName));
	}
}
