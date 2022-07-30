/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package gof_coffee_assignment;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Louis
 */
public class BeverageTest {
    
    public BeverageTest() {
    }

    /**
     * Test of getDesc method, of class Beverage.
     */
    @Test
    public void testGetDesc() {
        System.out.println("getDesc");
        Beverage instance = new BeverageImpl();
        String expResult = "";
        String result = instance.getDesc();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cost method, of class Beverage.
     */
    @Test
    public void testCost() {
        System.out.println("cost");
        Beverage instance = new BeverageImpl();
        double expResult = 0.0;
        double result = instance.cost();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class BeverageImpl extends Beverage {
    }
    
}
