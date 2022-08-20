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
public class SodaMachine {
    SodaState   current;
    public SodaMachine() {
        current = new ZeroState();
    }
    public  String CoinReturn()
    {
        return current.CoinReturn(this);
    };
    public  String CoinSlotDeposit(){
        return current.CoinSlotDeposit(this);
    }
    public  String SodaButton(){
        return current.SodaButton(this);
    }
    public void SetCurrent(SodaState s){
        current = s;
    }
}
