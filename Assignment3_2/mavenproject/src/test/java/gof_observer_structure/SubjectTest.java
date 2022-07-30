/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package gof_observer_structure;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;

public class SubjectTest {
    
	private static Subject s = new Subject();
	private static Observer1 o1 = new Observer1("Dr. Gumbo");
	
	@BeforeClass
    public static void setup() {
		
		System.out.println("***Setting up class Subject");
		s.attach(o1);
		s.setState(41, 7);
	}
    /**
     * Test of attach method, of class Subject.
     */
	
    @Test
    public void testAttach() {

        System.out.println("***Testing attach");

        o1 = new Observer1("Mrs. Hurricane");
        s.attach(o1);
    }

    /**
     * Test of detach method, of class Subject.
     */
    
    @Test
    public void testDetach() {
    	
        System.out.println("***Testing detach");
        o1 = new Observer1("Mr. Muffuletta");
        s.attach(o1);
        s.detach(o1);
    }

	/**
     * Test of Notify method, of class Subject.
     */
    @Test
    public void testNotify() {
        System.out.println("***Testing Notify");
        o1 = new Observer1("Mrs. Hurricane");
        s.Notify();
        
    }

    /**
     * Test of setState method, of class Subject.
     */
    @Test
    public void testSetState() {
        System.out.println("***Testing setState");
        s.setState(0, 0);
    }
    
}
