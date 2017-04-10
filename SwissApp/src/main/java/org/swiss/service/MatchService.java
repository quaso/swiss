package org.swiss.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiss.model.Match;
import org.swiss.model.Player;
import org.swiss.model.Tournament;
import org.swiss.repository.MatchRepository;

@Service
// @Transactional
public class MatchService {

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private PlayerService playerService;

	public Player getWinner(final Player player1, final Player player2) {
		Player result = null;
		final Optional<Match> match = this.findMatch(player1, player2);
		if (match.isPresent()) {
			result = this.getWinner(match.get());
		}
		return result;
	}

	public void addMatchResult(final String player1Name, final String player2Name, final int score1, final int score2) {
		final Match match = this.findMatch(player1Name, player2Name)
				.orElseThrow(() -> new IllegalStateException("Match not found"));

		if (player1Name.equals(match.getPlayer1().getName())) {
			match.setScore1(score1);
			match.setScore2(score2);
		} else {
			match.setScore1(score2);
			match.setScore2(score1);
		}

		final MatchResult matchResult = new MatchResult(match);
		matchResult.winner.setPoints(matchResult.winner.getPoints() + 1);
		final int score = Math.abs(score1 - score2);
		matchResult.winner.setScore(matchResult.winner.getScore() + score);
		matchResult.looser.setScore(matchResult.looser.getScore() - score);

		// playerRepository.save(matchResult.winner);
		// playerRepository.save(matchResult.looser);
		this.matchRepository.save(match);
	}

	public Optional<Match> findMatch(final String player1Name, final String player2Name) {
		return this.findMatch(this.playerService.find(player1Name), this.playerService.find(player2Name));
	}

	public Optional<Match> findMatch(final Player player1, final Player player2) {
		Optional<Match> result = this.matchRepository.findMatchByPlayer1IdAndPlayer2Id(player1.getId(),
				player2.getId());
		if (!result.isPresent()) {
			result = this.matchRepository.findMatchByPlayer1IdAndPlayer2Id(player2.getId(), player1.getId());
		}
		return result;
	}

	public Match createMatch(final Tournament tournament, final Player player1, final Player player2,
			final int roundNumber) {
		final Match match = new Match();
		match.setTournament(tournament);
		match.setPlayer1(player1);
		match.setPlayer2(player2);
		match.setRoundNumber(roundNumber);
		this.matchRepository.save(match);
		return match;
	}

	private Player getWinner(final Match match) {
		return match.getScore1() > match.getScore2() ? match.getPlayer1() : match.getPlayer2();
	}

	private class MatchResult {
		Player winner;
		Player looser;

		MatchResult(final Match match) {
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
