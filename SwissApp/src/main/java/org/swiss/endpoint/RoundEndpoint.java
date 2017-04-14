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

	@RequestMapping(method = RequestMethod.POST, value = "")
	public ResponseEntity<Round> addNewRound(final @RequestParam String tournamentName) {
		return new ResponseEntity<>(this.tournamentService.addNewRound(tournamentName), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "")
	public ResponseEntity<Round> getCurrent(final @RequestParam String tournamentName, @RequestParam(required = false) final Integer number) {
		if (number == null){
			return new ResponseEntity<>(this.roundService.getLatest(tournamentName), HttpStatus.OK);
		}
		return new ResponseEntity<>(this.roundService.getRound(tournamentName, number), HttpStatus.OK);
	}
}
