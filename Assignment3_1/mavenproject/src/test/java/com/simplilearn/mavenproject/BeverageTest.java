package com.simplilearn.mavenproject;

import static org.junit.Assert.*;

import org.junit.Test;

public class BeverageTest {

	@Test
	public void testGetDesc() {
		
		System.out.println("getDesc");
		Beverage b = new BeverageImpl(); // TODO
		
		String expResult = "unknown beverage";
		String result = b.getDesc();
		assertEquals(expResult, result); 
	}

	@Test
	public void testCost() {
		
		System.out.println("cost");
        Beverage instance = new BeverageImpl();
        double expResult = 0.0;
        double result = instance.cost();
        assertEquals(expResult, result, 0.0);
        
    
	}

	public class BeverageImpl extends Beverage {
	}

}
