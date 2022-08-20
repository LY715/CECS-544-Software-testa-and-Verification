/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statepattern;

/**
 *
 * @author Daddy
 */
public class TwentyfiveState extends SodaState {
    public String CoinReturn(SodaMachine sm){
        sm.SetCurrent(new ZeroState());
        return ("$0.25 returned");
    }
    public  String CoinSlotDeposit(SodaMachine sm){
        sm.SetCurrent(new FiftyState());
        return "Total entered: $0.50";
    }
    public  String SodaButton(SodaMachine sm){
        return ("Enter 25 cents more");
    }
}
