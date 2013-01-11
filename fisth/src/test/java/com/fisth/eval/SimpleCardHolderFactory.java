/*
 * This file is part of fisth, an FSM-based Texas Hold'em hand evaluator.
 * Copyright (C) 2010 Robert Strack <robert.strack@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.fisth.eval;

import java.util.Map;
import java.util.Scanner;

import com.fisth.Card;

public class SimpleCardHolderFactory {

	private Map<String, Card> cardMappings;

	public SimpleCardHolderFactory(Map<String, Card> cardMappings) {
		super();
		this.cardMappings = cardMappings;
	}

	public Board createBoard(String description) {
		Scanner scanner = new Scanner(description);
		try {
			Card flop1 = cardMappings.get(scanner.next());
			Card flop2 = cardMappings.get(scanner.next());
			Card flop3 = cardMappings.get(scanner.next());
			Card turn = cardMappings.get(scanner.next());
			Card river = cardMappings.get(scanner.next());
			return new Board(flop1, flop2, flop3, turn, river);
		} finally {
			scanner.close();
		}
	}

	public Hand createHand(String description) {
		Scanner scanner = new Scanner(description);
		try {
			Card card1 = cardMappings.get(scanner.next());
			Card card2 = cardMappings.get(scanner.next());
			return new Hand(card1, card2);
		} finally {
			scanner.close();
		}
	}
	
}
