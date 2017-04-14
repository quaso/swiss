package org.swiss.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
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
	public void test1() {
		this.tournamentService.createTournament(TOURNAMENT_NAME, true);
		final List<String> players = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");
		this.tournamentService.setPlayers(TOURNAMENT_NAME, players);
		final Round round1 = this.tournamentService.addNewRound(TOURNAMENT_NAME);

		this.matchService.addMatchResult("a", "b", 3, 4);
		this.matchService.addMatchResult("c", "d", 4, 2);
		this.matchService.addMatchResult("e", "f", 4, 1);
		this.matchService.addMatchResult("g", "h", 4, 0);
		this.print(round1);

		final Round round2 = this.tournamentService.addNewRound(TOURNAMENT_NAME);
		this.print(round2.getPlayers());
		Assert.assertNull(this.compare(round2, "g", "e", "c", "b", "a", "d", "f", "h"));

		this.matchService.addMatchResult("e", "g", 0, 4);
		this.matchService.addMatchResult("c", "b", 4, 3);
		this.matchService.addMatchResult("a", "d", 4, 3);
		this.matchService.addMatchResult("f", "h", 4, 3);
		this.print(round2);

		final Round round3 = this.tournamentService.addNewRound(TOURNAMENT_NAME);
		this.print(round3.getPlayers());
		this.print(round3);
		Assert.assertNull(this.compare(round3, "g", "c", "b", "a", "e", "f", "d", "h"));
	}

	@Test
	public void test2() {
		this.tournamentService.createTournament(TOURNAMENT_NAME, true);
		final List<String> players = Arrays.asList("a", "b", "c", "d");
		this.tournamentService.setPlayers(TOURNAMENT_NAME, players);
		final Round round1 = this.tournamentService.addNewRound(TOURNAMENT_NAME);

		this.matchService.addMatchResult("a", "b", 4, 2);
		this.matchService.addMatchResult("c", "d", 4, 2);
		this.print(round1);

		final Round round2 = this.tournamentService.addNewRound(TOURNAMENT_NAME);
		this.print(round2.getPlayers());
		Assert.assertNull(this.compare(round2, "a", "c", "b", "d"));
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

	private String compare(final Round round, final String... expectedOrder) {
		String result = null;
		for (int i = 0; i < expectedOrder.length; i++) {
			if (!expectedOrder[i].equals(round.getPlayers().get(i).getName())) {
				result = "Player at " + i + " is [" + round.getPlayers().get(i).getName() + "] expected ["
						+ expectedOrder[i] + "]";
				break;
			}
		}
		return result;
	}
}
