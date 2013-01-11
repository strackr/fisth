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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.testng.annotations.Test;

import com.fisth.Card;

public class CardIteratorTest {

	@Test(expectedExceptions = NoSuchElementException.class)
	public void shouldFailToYieldACardWhenEmpty() {
		CardHolder holder = new CardHolder();
		CardIterator iter = new CardIterator(holder);

		iter.next();
	}

	@Test
	public void shouldYieldACardWhenNonempty() {
		Card card = Card.ACE_OF_SPADES;
		CardHolder nonemptyHolder = new CardHolder(card);
		CardIterator iter = new CardIterator(nonemptyHolder);

		Card obtainedCard = iter.next();
		
		assertSame(obtainedCard, card);
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void shouldSupportMultipleNonemptyCardHolders() {
		Card firstCard = Card.ACE_OF_SPADES;
		Card secondCard = Card.KING_OF_SPADES;
		CardHolder holderWithTwoCards = new CardHolder(firstCard, secondCard);
		Card thirdCard = Card.QUEEN_OF_SPADES;
		CardHolder holderWithOneCard = new CardHolder(thirdCard);
		CardIterator iter = new CardIterator(holderWithTwoCards, holderWithOneCard);

		Card firstObtainedCard = iter.next();
		Card secondObtainedCard = iter.next();
		Card thirdObtainedCard = iter.next();
		
		assertSame(firstObtainedCard, firstCard);
		assertSame(secondObtainedCard, secondCard);
		assertSame(thirdObtainedCard, thirdCard);
		assertFalse(iter.hasNext());
	}

	@Test
	public void shouldSkipEmptyCardHoldersWhenIterating() {
		CardHolder emptyHolder = new CardHolder();
		Card card = Card.ACE_OF_SPADES;
		CardHolder nonemptyHolder = new CardHolder(card);
		CardIterator iter = new CardIterator(emptyHolder, nonemptyHolder);
		
		Card obtainedCard = iter.next();
		
		assertSame(obtainedCard, card);
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void shouldSkipEmptyCardHoldersWhenCheckingIfHasNext() {
		CardHolder emptyHolder = new CardHolder();
		Card card = Card.ACE_OF_SPADES;
		CardHolder nonemptyHolder = new CardHolder(card);
		CardIterator iter = new CardIterator(emptyHolder, nonemptyHolder);
		
		boolean hasNextAfterInitialization = iter.hasNext();
		
		assertTrue(hasNextAfterInitialization);
	}
	
	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void shouldFailOnRemove() {
		CardHolder nonemptyHolder = new CardHolder(Card.ACE_OF_SPADES);
		CardIterator iter = new CardIterator(nonemptyHolder);

		iter.next();
		iter.remove();
	}

}
