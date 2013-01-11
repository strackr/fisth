package com.fisth.eval;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class HandRankTest {

	@Test
	public void shouldBeEqualToTheHandRankHavingTheSameRankValue() {
		HandRank rank = new HandRank(1);
		HandRank sameRank = new HandRank(1);
		
		boolean ranksEquality = rank.equals(sameRank);
		
		assertTrue(ranksEquality);
	}
	
	@Test
	public void shouldNotBeEqualToTheHandRankHavingDifferentRankValue() {
		HandRank lower = new HandRank(1);
		HandRank higher = new HandRank(2);
		
		boolean handRanksEquality = lower.equals(higher);
		
		assertFalse(handRanksEquality);
	}

	@Test
	public void shouldBeEqualToTheHandRankHavingTheSameRankValueWhenUsedAsComparable() {
		HandRank rank = new HandRank(1);
		HandRank sameRank = new HandRank(1);
		
		int comparison = rank.compareTo(sameRank);
		
		assertEquals(comparison, 0);
	}
	
	@Test
	public void shouldBeGreaterThanAHandRankHavingLowerRankValueWhenUsedAsComparable() {
		HandRank lower = new HandRank(1);
		HandRank higher = new HandRank(2);
		
		int comparisonHighWithLow = higher.compareTo(lower);
		
		assertTrue(comparisonHighWithLow > 0);
	}

	@Test
	public void shouldBeLessThanAHandRankHavingGreaterRankValueWhenUsedAsComparable() {
		HandRank lower = new HandRank(1);
		HandRank higher = new HandRank(2);
		
		int comparisonLowWithHigh = lower.compareTo(higher);
		
		assertTrue(comparisonLowWithHigh < 0);
	}
	
}
