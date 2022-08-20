package statepattern;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class SodaMachineTest {
	
    @Test
    public void testSetCurrent() {
        System.out.println("SetCurrent to FiftyState");
        SodaMachine sodaMachine = new SodaMachine();
        FiftyState fiftyState = new FiftyState();
        sodaMachine.SetCurrent(fiftyState);
        assertEquals(fiftyState, sodaMachine.current);
    }

    @Test
    public void testSodaButton() {
        SodaMachine sodaMachine = new SodaMachine();

        System.out.println("SodaButton-ZeroState");
        assertEquals(ZeroState.class, sodaMachine.current.getClass());
        assertEquals("Deposit 50 cents first", sodaMachine.SodaButton());

        System.out.println("SodaButton-TwentyfiveState");
        sodaMachine.SetCurrent(new TwentyfiveState());
        assertEquals("Enter 25 cents more", sodaMachine.SodaButton());
        assertEquals(TwentyfiveState.class, sodaMachine.current.getClass());

        System.out.println("SodaButton-FiftyState");
        sodaMachine.SetCurrent(new FiftyState());
        
        assertEquals("Your soda has been vended", sodaMachine.SodaButton());
        assertEquals(ZeroState.class, sodaMachine.current.getClass());
    }

    @Test
    public void tesCoinSlotDeposit() {
        SodaMachine sodaMachine = new SodaMachine();
        
        System.out.println("CoinSlotDeposit-ZeroState");
        assertEquals("Total entered: $0.25", sodaMachine.CoinSlotDeposit());
        assertEquals(TwentyfiveState.class, sodaMachine.current.getClass());

        System.out.println("CoinSlotDeposit-TwentyfiveState");
        assertEquals("Total entered: $0.50", sodaMachine.CoinSlotDeposit());
        assertEquals(FiftyState.class, sodaMachine.current.getClass());

        System.out.println("CoinSlotDeposit-FiftyState");
        assertEquals("Coin returned - $0.50 already deposited", sodaMachine.CoinSlotDeposit());
        assertEquals(FiftyState.class, sodaMachine.current.getClass());


    }

    @Test
    public void testCoinReturn() {
        SodaMachine sodaMachine = new SodaMachine();

        System.out.println("CoinReturn-ZeroState");
        assertEquals("Return $0.00", sodaMachine.CoinReturn());
        assertEquals(ZeroState.class, sodaMachine.current.getClass());

        System.out.println("CoinReturn-TwentyfiveState");
        sodaMachine.SetCurrent(new TwentyfiveState());
        assertEquals("$0.25 returned", sodaMachine.CoinReturn());
        assertEquals(ZeroState.class, sodaMachine.current.getClass());

        System.out.println("CoinReturn-FiftyState");
        sodaMachine.SetCurrent(new FiftyState());
        assertEquals("$0.50 returned", sodaMachine.CoinReturn());
        assertEquals(ZeroState.class, sodaMachine.current.getClass());
    }
}
