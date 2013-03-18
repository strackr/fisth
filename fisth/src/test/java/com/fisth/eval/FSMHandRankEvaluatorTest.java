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

import static com.fisth.Card.ACE_OF_SPADES;
import static com.fisth.Card.JACK_OF_HEARTS;
import static com.fisth.Card.JACK_OF_SPADES;
import static com.fisth.Card.KING_OF_HEARTS;
import static com.fisth.Card.KING_OF_SPADES;
import static com.fisth.Card.QUEEN_OF_HEARTS;
import static com.fisth.Card.QUEEN_OF_SPADES;
import static com.fisth.Card.TWO_OF_CLUBS;
import static com.fisth.Card.TWO_OF_DIAMONDS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fisth.Card;

public class FSMHandRankEvaluatorTest {

	private HandRankEvaluator evaluator;
	
	@BeforeMethod
	public void setUp() {
		ConfigurationLoader resourceLoader = new ConfigurationLoader();
		FSMHandRankEvaluatorFactory evaluatorFactory = new FSMHandRankEvaluatorFactory(
				resourceLoader);
		evaluator = evaluatorFactory.create();
	}
	
	@DataProvider(name = "card-configurations-with-rank")
	public Object[][] cardCofnigurationsWithRank() {
		Map<String, Card> cardMappings = new HashMap<String, Card>();
		for (Card card : Card.values()) {
			cardMappings.put(card.toString(), card);
		}
		SimpleCardHolderFactory bld = new SimpleCardHolderFactory(cardMappings);

		// precomputed hand ranks
		return new Object[][] {
				// true rank, board, hand
				{ 1276, bld.createBoard("As Kh Qd Jc 9s"), bld.createHand("8h 7d") },
				{ 4136, bld.createBoard("Qs Ah Kd Jc As"), bld.createHand("9h 8d") },
				{ 4994, bld.createBoard("Qs Ah Kd Jc As"), bld.createHand("9h Ks") },
				{ 5852, bld.createBoard("Qs Ah Kd Jc As"), bld.createHand("Ac 9s") },
				{ 5853, bld.createBoard("2s 4h 3d 5c 8s"), bld.createHand("6c 8s") },
				{ 5862, bld.createBoard("Qs Ah Kd Jc As"), bld.createHand("Ac Ts") },
				{ 5863, bld.createBoard("2s 4s 3s 5s Ah"), bld.createHand("7s Ac") },
				{ 7139, bld.createBoard("Qh Ah Kh Jh As"), bld.createHand("9h Ts") },
				{ 7140, bld.createBoard("2s 3h 3s 5s 2c"), bld.createHand("7s 2d") },
				{ 7295, bld.createBoard("Ks 5s Ac 7s Ad"), bld.createHand("As Kh") },
				{ 7451, bld.createBoard("As Ah Ad Ac 9s"), bld.createHand("8h Kd") },
				{ 7452, bld.createBoard("3h 4c Ac 2h 6h"), bld.createHand("5h 4h") },
				{ 7461, bld.createBoard("Ac Qc 5h Tc Jc"), bld.createHand("2c Kc") }
		};
	}

	@Test(dataProvider = "card-configurations-with-rank")
	public void shouldCorrectlyClassifyPokerHands(int trueRank, Board board, Hand hand) {
		HandRank rank = evaluator.evaluate(board, hand);
		
		assertEquals(rank.getValue(), trueRank);
	}

	@Test
	public void shouldClassifyTwoAndThreePairHands() {
		Board twoPairBoard = new Board(KING_OF_SPADES, KING_OF_HEARTS,
				JACK_OF_SPADES, JACK_OF_HEARTS, TWO_OF_CLUBS);
		Hand threePairHand = new Hand(QUEEN_OF_SPADES, QUEEN_OF_HEARTS);
		Hand twoPairHand = new Hand(ACE_OF_SPADES, TWO_OF_DIAMONDS);
		
		HandRank threePairRank = evaluator.evaluate(twoPairBoard, threePairHand);
		HandRank twoPairRank = evaluator.evaluate(twoPairBoard, twoPairHand);
		
		assertTrue(threePairRank.getValue() > twoPairRank.getValue());
	}

}
