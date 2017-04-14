package org.swiss.util;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.swiss.model.IPlayerScore;
import org.swiss.service.MatchService;

@Component
public class PlayerComparator implements Comparator<IPlayerScore> {

	@Autowired
	private MatchService matchService;

	private List<? extends IPlayerScore> previousOrder;

	public synchronized void compare(final List<? extends IPlayerScore> players,
			final List<? extends IPlayerScore> previousOrder) {
		this.previousOrder = previousOrder;
		players.sort(this);
	}

	@Override
	public int compare(final IPlayerScore p1, final IPlayerScore p2) {
		// 1. compare points
		int result = -1 * Integer.compare(p1.getPoints(), p2.getPoints());
		if (result == 0) {
			// 2. compare score
			result = -1 * Integer.compare(p1.getScore(), p2.getScore());
			if (result == 0) {
				// 3. compare match against each other
				final IPlayerScore winner = this.matchService.getWinner(p1, p2);
				if (winner != null) {
					result = p1.getName().equals(winner.getName()) ? -1 : 1;
				} else {
					// 4. decide based on order in previous round
					for (int i = 0; i < this.previousOrder.size(); i++) {
						final IPlayerScore p = this.previousOrder.get(i);
						if (p1.equals(p)) {
							result = -1;
							break;
						}
						if (p2.equals(p)) {
							result = 1;
							break;
						}
					}
				}
			}
		}

		return result;
	}
}