import static org.junit.Assert.*;

import org.junit.Test;

public class ExpressoTest {

	@Test
	public void testCost() {
		
		Beverage b = new Expresso();
		
		double expResult = 1.99;
		double result = b.cost();
		
		assertEquals(expResult, result, 0);
	}
	
	@Test
	public void testGetDesc() {
		
		Beverage b = new Expresso();
		
		String expResult = "Expresso";
		String result = b.getDesc();
		
		assertEquals(expResult, result);
	}

}
