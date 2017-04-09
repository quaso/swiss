package org.swiss.repository;

import java.util.Optional;

import org.swiss.model.Tournament;

//@Transactional
public interface TournamentRepository {// extends CrudRepository<Tournament,
										// String> {
	public Optional<Tournament> findByName(String name);

	public void save(Tournament tournament);

	public void delete(Tournament tournament);
}
