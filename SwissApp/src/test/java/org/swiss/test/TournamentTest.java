package org.swiss.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.swiss.model.Match;
import org.swiss.model.Player;
import org.swiss.model.Round;
import org.swiss.service.MatchService;
import org.swiss.service.TournamentService;
import org.swiss.test.conf.TestConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@ActiveProfiles("test")
// @Transactional
public class TournamentTest {

	private static final String TOURNAMENT_NAME = "test";

	@Autowired
	private TournamentService tournamentService;

	@Autowired
	private MatchService matchService;

	@Test
	public void test() {
		this.tournamentService.createTournament(TOURNAMENT_NAME, false);
		final List<String> players = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");
		this.tournamentService.setPlayers(TOURNAMENT_NAME, players);
		final Round round1 = this.tournamentService.addNewRound(TOURNAMENT_NAME);
		this.print(round1);

		this.matchService.addMatchResult(TOURNAMENT_NAME, 1, "a", "b", 4, 3);
		this.matchService.addMatchResult(TOURNAMENT_NAME, 1, "c", "d", 4, 2);
		this.matchService.addMatchResult(TOURNAMENT_NAME, 1, "e", "f", 4, 1);
		this.matchService.addMatchResult(TOURNAMENT_NAME, 1, "g", "h", 4, 0);
		this.print(round1);

		final Round round2 = this.tournamentService.addNewRound(TOURNAMENT_NAME);
		this.print(round2.getPlayers());
		this.print(round2);

	}

	private void print(final Round round) {
		for (final Match m : round.getMatches()) {
			System.out.println(m.toString());
		}
	}

	private void print(final List<Player> players) {
		for (final Player p : players) {
			System.out.println(p.toStringExtended());
		}
	}
}
