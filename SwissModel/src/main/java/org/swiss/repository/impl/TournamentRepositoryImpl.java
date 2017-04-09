package org.swiss.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.swiss.model.Tournament;
import org.swiss.repository.TournamentRepository;

@Repository
public class TournamentRepositoryImpl implements TournamentRepository {

	private final Map<String, Tournament> map = new HashMap<>();

	@Override
	public Optional<Tournament> findByName(final String name) {
		if (this.map.containsKey(name)) {
			return Optional.of(this.map.get(name));
		}
		return Optional.empty();
	}

	@Override
	public void save(final Tournament tournament) {
		this.map.put(tournament.getName(), tournament);
	}

	@Override
	public void delete(final Tournament tournament) {
		this.map.remove(tournament.getName());
	}

}
