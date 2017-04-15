package org.swiss.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.swiss.model.IPlayerScore;
import org.swiss.model.Match;
import org.swiss.model.Round;

public class RoundPredicter {

	@Autowired
	private PlayerComparator playerComparator;

	private String[] order;

	private int maxScorePerRound;

	public synchronized List<String> predictNextRoundOrder(final Round current, final int maxScorePerRound) {
		this.order = null;
		this.maxScorePerRound = maxScorePerRound;
		final Map<String, FuturePlayer> players = new HashMap<>();
		current.getPlayers().forEach(p -> players.put(p.getName(), new FuturePlayer(p)));

		// iterate over completed matches and get list of not yet played matches
		final List<Match> unfinishedMacthes = current.getMatches().stream().filter(m -> !m.isFinished())
				.collect(Collectors.toList());
		this.combi(unfinishedMacthes, 0, players, current.getPlayers());
		return Arrays.asList(this.order);
	}

	private boolean combi(final List<Match> unfinishedMacthes, final int index, final Map<String, FuturePlayer> players,
			final List<? extends IPlayerScore> previousOrder) {
		if (index < unfinishedMacthes.size()) {
			final List<Pair> combis = this.getMatchScoreCombinations(unfinishedMacthes.get(index));
			for (final Pair p : combis) {
				players.put(p.player1.getName(), p.player1);
				players.put(p.player2.getName(), p.player2);
				if (!this.combi(unfinishedMacthes, index + 1, players, previousOrder)) {
					return false;
				}
			}
			return true;
		} else {
			final ArrayList<FuturePlayer> players2 = new ArrayList<>(players.values());
			this.playerComparator.compare(players2, previousOrder);
			if (this.order == null) {
				this.order = new String[players2.size()];
				for (int i = 0; i < players2.size(); i++) {
					this.order[i] = players2.get(i).getName();
				}
				return true;
			} else {
				int fixedOrder = 0;
				for (int i = 0; i < players2.size(); i++) {
					if (this.order[i] != null) {
						if (this.order[i].equals(players2.get(i).getName())) {
							fixedOrder++;
						} else {
							this.order[i] = null;
						}
					}
				}
				return fixedOrder > 0;
			}
		}
	}

	private List<Pair> getMatchScoreCombinations(final Match match) {
		final List<Pair> result = new ArrayList<>();
		int score = this.maxScorePerRound + 1;
		final FuturePlayer player1 = new FuturePlayer(match.getPlayer1());
		final FuturePlayer player2 = new FuturePlayer(match.getPlayer2());
		for (int i = 0; i < this.maxScorePerRound; i++) {
			score--;
			result.add(new Pair(player1.matchResult(true, score), player2.matchResult(false, score), score));
		}
		score = this.maxScorePerRound + 1;
		for (int i = 0; i < this.maxScorePerRound; i++) {
			score--;
			result.add(new Pair(player1.matchResult(false, score), player2.matchResult(true, score), score));
		}
		return result;
	}

	private class FuturePlayer implements IPlayerScore {
		String name;
		int points;
		int score;
		String tournamentName;

		public FuturePlayer(final IPlayerScore player) {
			this.name = player.getName();
			this.points = player.getPoints();
			this.score = player.getScore();
			this.tournamentName = player.getTournamentName();
		}

		private FuturePlayer(final IPlayerScore player, final int points, final int score) {
			this.name = player.getName();
			this.points = points;
			this.score = score;
			this.tournamentName = player.getTournamentName();
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public int getPoints() {
			return this.points;
		}

		@Override
		public int getScore() {
			return this.score;
		}

		@Override
		public String getTournamentName() {
			return this.tournamentName;
		}

		public FuturePlayer matchResult(final boolean winner, final int absScore) {
			FuturePlayer result;
			if (winner) {
				result = new FuturePlayer(this, this.points + 1, this.score + absScore);
			} else {
				result = new FuturePlayer(this, this.points, this.score - absScore);
			}
			return result;
		}

		@Override
		public String toString() {
			return this.toStringExtended();
		}
	}

	private class Pair {
		FuturePlayer player1;
		FuturePlayer player2;
		private final int score;

		Pair(final FuturePlayer player1, final FuturePlayer player2, final int score) {
			this.player1 = player1;
			this.player2 = player2;
			this.score = score;
		}

		@Override
		public String toString() {
			return "Pair [score=" + this.score + "]";
		}

	}
}
