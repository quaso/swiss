package org.swiss.repository;

import org.swiss.model.Round;

//@Transactional
public interface RoundRepository {// extends CrudRepository<Round, String> {
	public int countByTournamentName(String tournamentName);

	public Round getRoundByNumber(String tournamentName, int number);

	public void save(Round round);

	public void deleteByTournamentName(String tournamentName);
}
