package org.swiss.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.swiss.service.MatchService;

@RestController
@RequestMapping("/api/match")
public class MatchEndpoint {

	@Autowired
	private MatchService matchService;

	@RequestMapping(method = RequestMethod.POST, value = "/")
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(final @RequestParam String player1Name, final @RequestParam String player2Name,
			final @RequestParam int score1, final @RequestParam int score2) {
		this.matchService.addMatchResult(player1Name, player2Name, score1, score2);
	}
}
