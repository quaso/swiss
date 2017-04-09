package org.swiss.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiss.model.Match;
import org.swiss.model.Player;
import org.swiss.model.Tournament;
import org.swiss.repository.MatchRepository;
import org.swiss.repository.PlayerRepository;

@Service
// @Transactional
public class MatchService {

	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private RoundService roundService;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private PlayerRepository playerRepository;

	public Player getWinner(final Player player1, final Player player2) {
		Player result = null;
		final Optional<Match> match = this.findMatch(player1, player2);
		if (match.isPresent()) {
			result = this.getWinner(match.get());
		}
		return result;
	}

	public void addMatchResult(final String tournamentName, final int roundNumber, final String player1Name,
			final String player2Name, final int score1, final int score2) {
		this.addMatchResult(tournamentName, roundNumber, player1Name, player2Name, score1, score2, 0, 0);
	}

	public void addMatchResult(final String tournamentName, final int roundNumber, final String player1Name,
			final String player2Name, final int score1, final int score2, final int subScore1, final int subScore2) {
		final Tournament tournament = this.tournamentService.find(tournamentName);
		final Match match = this.roundService.findMatch(tournament, roundNumber, player1Name, player2Name)
				.orElseThrow(() -> new IllegalStateException("Match not found"));
		match.setScore1(score1);
		match.setScore2(score2);
		match.setSubScore1(subScore1);
		match.setSubScore2(subScore2);

		final MatchResult matchResult = new MatchResult(match);
		matchResult.winner.setPoints(matchResult.winner.getPoints() + 1);
		final int score = Math.abs(score1 - score2);
		matchResult.winner.setScore(matchResult.winner.getScore() + score);
		matchResult.looser.setScore(matchResult.looser.getScore() - score);

		// playerRepository.save(matchResult.winner);
		// playerRepository.save(matchResult.looser);
		this.matchRepository.save(match);
	}

	public Optional<Match> findMatch(final Player player1, final Player player2) {
		return this.matchRepository.findMatchByPlayer1IdAndPlayer2Id(player1.getId(), player2.getId());
	}

	public Match createMatch(final Tournament tournament, final Player player1, final Player player2) {
		final Match match = new Match();
		match.setTournament(tournament);
		match.setPlayer1(player1);
		match.setPlayer2(player2);
		this.matchRepository.save(match);
		return match;
	}

	private Player getWinner(final Match match) {
		return (match.getScore1() > match.getScore2()) ? match.getPlayer1() : match.getPlayer2();
	}

	private class MatchResult {
		Player winner;
		Player looser;

		private MatchResult(final Match match) {
			if (match.getScore1() > match.getScore2()) {
				this.winner = match.getPlayer1();
				this.looser = match.getPlayer2();
			} else {
				this.winner = match.getPlayer2();
				this.looser = match.getPlayer1();
			}
		}
	}
}
