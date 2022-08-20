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
public class FiftyState extends SodaState {
    public  String CoinReturn(SodaMachine sm){
        sm.SetCurrent(new ZeroState());
        return "$0.50 returned";
    }
    public  String CoinSlotDeposit(SodaMachine sm){
        return "Coin returned - $0.50 already deposited";
    }
    public  String SodaButton(SodaMachine sm) {
        sm.SetCurrent(new ZeroState());
        return "Your soda has been vended";
    }
}
