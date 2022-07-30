/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gof_observer_structure;

/**
 *
 * @author Daddy
 */
public class Observer2 extends Observer {

    public Observer2(String n){
        name = n;
    }
    @Override
    public void update(int x, int y) {
       System.out.println("I am "+name+ " being updated by Subject with x="+x+" y="+y);
    }
    
}
