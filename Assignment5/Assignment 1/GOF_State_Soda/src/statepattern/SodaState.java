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
public abstract class SodaState {
    public abstract String CoinReturn(SodaMachine sm);
    public abstract String CoinSlotDeposit(SodaMachine sm);
    public abstract String SodaButton(SodaMachine sm);
}
