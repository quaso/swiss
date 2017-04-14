package org.swiss.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.swiss.service.TournamentService;

@RestController
@RequestMapping("/api/tournament")
public class TournamentEndpoint {

	@Autowired
	private TournamentService tournamentService;

	@RequestMapping(method = RequestMethod.POST, value = "/")
	public ResponseEntity<?> createTournament(final @RequestParam String tournamentName,
			@RequestParam final boolean overwrite, final @RequestBody List<String> players) {
		ResponseEntity<?> result;
		final boolean tournamentCreated = this.tournamentService.createTournament(tournamentName, overwrite);
		if (!tournamentCreated) {
			if (overwrite) {
				// fatal error
				result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				result = new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		} else {
			this.tournamentService.setPlayers(tournamentName, players);
			result = new ResponseEntity<>(HttpStatus.CREATED);
		}

		return result;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/replacePlayer")
	@ResponseStatus(HttpStatus.OK)
	public void replacePlayer(final @RequestParam String tournamentName, final @RequestParam String oldName,
			final @RequestParam String newName) {
		this.tournamentService.replacePlayer(tournamentName, oldName, newName);
	}
}
