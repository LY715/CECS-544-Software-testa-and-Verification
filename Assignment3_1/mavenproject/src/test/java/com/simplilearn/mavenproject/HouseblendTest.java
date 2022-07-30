package com.simplilearn.mavenproject;
import static org.junit.Assert.*;

import org.junit.Test;

public class HouseblendTest {

	
	@Test
	public void testCost() {
		Beverage b = new Houseblend();
		
		double expResult = 0.99;
		double result = b.cost();
		
		assertEquals(expResult, result, 0);
	}
	
	@Test
	public void testGetDesc() {
		
		Beverage b = new Houseblend();
		
		String expResult = "Houseblend";
		String result = b.getDesc();
		
		
		assertEquals(expResult, result);
	}

}
