package org.swiss.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.swiss.model.Round;
import org.swiss.repository.RoundRepository;

@Repository
public class RoundRepositoryImpl implements RoundRepository {

	private final Map<String, List<Round>> map = new HashMap<>();

	@Override
	public int countByTournamentName(final String tournamentName) {
		return this.map.getOrDefault(tournamentName, Collections.emptyList()).size();
	}

	@Override
	public Round getRoundByNumber(final String tournamentName, final int number) {
		return this.map.get(tournamentName).get(number - 1);
	}

	@Override
	public void save(final Round round) {
		this.map.putIfAbsent(round.getTournamentName(), new ArrayList<>());
		this.map.get(round.getTournamentName()).add(round);
	}

	@Override
	public void deleteByTournamentName(final String tournamentName) {
		this.map.remove(tournamentName);
	}

}
