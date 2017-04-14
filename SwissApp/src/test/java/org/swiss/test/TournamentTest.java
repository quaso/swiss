package org.swiss.test;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.swiss.endpoint.RoundEndpoint;
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

	@Autowired
	private RoundEndpoint roundEndpoint;

	@Test
	public void test0() {
		this.tournamentService.createTournament(TOURNAMENT_NAME, true);
		final List<String> players = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");
		this.tournamentService.setPlayers(TOURNAMENT_NAME, players, false);
		Assert.assertEquals(HttpStatus.CREATED, this.roundEndpoint.getRound(TOURNAMENT_NAME, null).getStatusCode());
	}

	@Test
	public void test1() {
		this.tournamentService.createTournament(TOURNAMENT_NAME, true);
		final List<String> players = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");
		this.tournamentService.setPlayers(TOURNAMENT_NAME, players, false);
		// final Round round1 =
		// this.tournamentService.addNewRound(TOURNAMENT_NAME);
		final Round round1 = this.roundEndpoint.getRound(TOURNAMENT_NAME, null).getBody();
		Assert.assertEquals(1, round1.getNumber());

		this.matchService.addMatchResult(TOURNAMENT_NAME, "a", "b", 3, 4);
		this.matchService.addMatchResult(TOURNAMENT_NAME, "c", "d", 4, 2);
		this.matchService.addMatchResult(TOURNAMENT_NAME, "e", "f", 4, 1);
		this.matchService.addMatchResult(TOURNAMENT_NAME, "g", "h", 4, 0);
		this.print(round1);

		final Round round2 = this.tournamentService.addNewRound(TOURNAMENT_NAME);
		Assert.assertEquals(2, round2.getNumber());
		this.print(round2.getPlayers());
		Assert.assertNull(this.compare(round2, "g", "e", "c", "b", "a", "d", "f", "h"));

		Assert.assertNull(this.compare(this.roundEndpoint.getRound(TOURNAMENT_NAME, 3).getBody(), null, null, null,
				null, null, null, null, null));
		this.matchService.addMatchResult(TOURNAMENT_NAME, "e", "g", 0, 4);
		Assert.assertNull(this.compare(this.roundEndpoint.getRound(TOURNAMENT_NAME, 3).getBody(), "g", null, null, null,
				null, null, null, null));
		this.matchService.addMatchResult(TOURNAMENT_NAME, "c", "b", 4, 3);
		Assert.assertNull(this.compare(this.roundEndpoint.getRound(TOURNAMENT_NAME, 3).getBody(), "g", "c", null, null,
				null, null, null, null));
		this.matchService.addMatchResult(TOURNAMENT_NAME, "a", "d", 4, 3);
		final ResponseEntity<Round> round3ResponseEntity = this.roundEndpoint.getRound(TOURNAMENT_NAME, 3);
		Assert.assertEquals(HttpStatus.PARTIAL_CONTENT, round3ResponseEntity.getStatusCode());
		Assert.assertNull(this.compare(round3ResponseEntity.getBody(), "g", "c", null, null, null, null, "d", null));
		this.matchService.addMatchResult(TOURNAMENT_NAME, "f", "h", 4, 3);
		this.print(round2);

		Assert.assertEquals(HttpStatus.NO_CONTENT, this.roundEndpoint.getRound(TOURNAMENT_NAME, 0).getStatusCode());
		Assert.assertEquals(HttpStatus.OK, this.roundEndpoint.getRound(TOURNAMENT_NAME, 1).getStatusCode());
		Assert.assertEquals(HttpStatus.OK, this.roundEndpoint.getRound(TOURNAMENT_NAME, 2).getStatusCode());
		Assert.assertEquals(HttpStatus.CREATED, this.roundEndpoint.getRound(TOURNAMENT_NAME, 3).getStatusCode());
		Assert.assertEquals(HttpStatus.PARTIAL_CONTENT,
				this.roundEndpoint.getRound(TOURNAMENT_NAME, 4).getStatusCode());
		Assert.assertEquals(HttpStatus.NO_CONTENT, this.roundEndpoint.getRound(TOURNAMENT_NAME, 5).getStatusCode());

		final Round round3 = this.roundEndpoint.getRound(TOURNAMENT_NAME, null).getBody();
		Assert.assertEquals(3, round3.getNumber());
		this.print(round3.getPlayers());
		this.print(round3);
		Assert.assertNull(this.compare(round3, "g", "c", "b", "a", "e", "f", "d", "h"));
		Assert.assertNull(this.compare(this.roundEndpoint.getRound(TOURNAMENT_NAME, 4).getBody(), null, null, null,
				null, null, null, null, null));
	}

	@Test
	public void test2() {
		this.tournamentService.createTournament(TOURNAMENT_NAME, true);
		final List<String> players = Arrays.asList("a", "b", "c", "d");
		this.tournamentService.setPlayers(TOURNAMENT_NAME, players, false);
		final Round round1 = this.tournamentService.addNewRound(TOURNAMENT_NAME);

		this.matchService.addMatchResult(TOURNAMENT_NAME, "a", "b", 4, 2);
		this.matchService.addMatchResult(TOURNAMENT_NAME, "c", "d", 4, 2);
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
			final Player player = round.getPlayers().get(i);
			final String realName = (player != null) ? player.getName() : null;
			if (!StringUtils.equals(expectedOrder[i], realName)) {
				result = "Player at " + i + " is [" + realName + "] expected [" + expectedOrder[i] + "]";
				break;
			}
		}
		return result;
	}
}
