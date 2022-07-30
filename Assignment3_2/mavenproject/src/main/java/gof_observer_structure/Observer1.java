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
public class Observer1 extends Observer {

    public Observer1(String n){
        name = n;
    }
    @Override
    public void update(int x, int y) {
       System.out.println("I am "+name+" being updated by Subject that x="+x+" and y="+y);
    }
    
}
