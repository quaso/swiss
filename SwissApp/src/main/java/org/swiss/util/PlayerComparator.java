package org.swiss.util;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.swiss.model.Player;
import org.swiss.service.MatchService;

@Component
public class PlayerComparator implements Comparator<Player> {

	@Autowired
	private MatchService matchService;

	private List<Player> previousOrder;

	@Override
	public int compare(final Player p1, final Player p2) {
		// 1. compare points
		int result = -1 * Integer.compare(p1.getPoints(), p2.getPoints());
		if (result == 0) {
			// 2. compare match against each other
			final Player winner = this.matchService.getWinner(p1, p2);
			if (winner != null) {
				result = (p1.equals(winner)) ? -1 : 1;
			} else {
				// 3. compare score
				result = -1 * Integer.compare(p1.getScore(), p2.getScore());
				if (result == 0) {
					// 4. decide based on order in previous round
					for (int i = 0; i < this.previousOrder.size(); i++) {
						final Player p = this.previousOrder.get(i);
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

	public void setPreviousOrder(final List<Player> previousOrder) {
		this.previousOrder = previousOrder;
	}
}