package org.swiss.repository;

import java.util.Optional;

import org.swiss.model.Match;

//@Transactional
public interface MatchRepository {// extends CrudRepository<Match, String> {

	Optional<Match> findMatchByPlayer1IdAndPlayer2Id(String player1Id, String player2Id);

	void save(Match match);
}
