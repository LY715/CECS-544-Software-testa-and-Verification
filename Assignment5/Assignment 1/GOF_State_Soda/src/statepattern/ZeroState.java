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
public class ZeroState extends SodaState {
    public String CoinReturn(SodaMachine sm){
        return  "Return $0.00";
    }
    public  String CoinSlotDeposit(SodaMachine sm){
       sm.SetCurrent(new TwentyfiveState());
       return ("Total entered: $0.25");
    }
    public  String SodaButton(SodaMachine sm){
        return "Deposit 50 cents first";
    }
    
}
