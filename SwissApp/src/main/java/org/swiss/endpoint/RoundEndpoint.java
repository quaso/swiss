package org.swiss.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.swiss.model.Round;
import org.swiss.service.RoundService;
import org.swiss.service.TournamentService;

@RestController
@RequestMapping("/api/round")
public class RoundEndpoint {

	@Autowired
	private RoundService roundService;

	@Autowired
	private TournamentService tournamentService;

	@RequestMapping(method = RequestMethod.GET, value = "")
	public ResponseEntity<Round> getRound(final @RequestParam String tournamentName,
			@RequestParam(required = false) final Integer number) {
		ResponseEntity<Round> result;

		final int maxRounds = this.roundService.getRoundCount(tournamentName);
		final int roundNumber = (number == null) ? maxRounds : number;
		if (roundNumber == 0 && maxRounds == 0) {
			// create first round
			result = new ResponseEntity<>(this.tournamentService.addNewRound(tournamentName), HttpStatus.CREATED);
		} else if (roundNumber <= 0) {
			result = new ResponseEntity<Round>(HttpStatus.NO_CONTENT);
		} else if (roundNumber <= maxRounds) {
			result = new ResponseEntity<>(this.roundService.getRound(tournamentName, roundNumber), HttpStatus.OK);
		} else if (roundNumber == maxRounds + 1) {
			// check if all matches in latest round are finished
			if (this.roundService.areAllMatchesFinished(tournamentName, roundNumber - 1)) {
				// create new round
				result = new ResponseEntity<>(this.tournamentService.addNewRound(tournamentName), HttpStatus.CREATED);
			} else {
				// predict
				result = new ResponseEntity<Round>(HttpStatus.I_AM_A_TEAPOT);
			}
		} else {
			result = new ResponseEntity<Round>(HttpStatus.NO_CONTENT);
		}
		return result;
	}
}
