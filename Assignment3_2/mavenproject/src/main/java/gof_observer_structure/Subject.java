/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gof_observer_structure;

///this is a concrete subject without an abstract parent

import java.util.ArrayList;

public class Subject {
    private int x, y;
    private ArrayList<Observer> list = new ArrayList();
    
    public boolean attach(Observer o){
        System.out.println("Adding "+o.name);
        if(o!=null){
            list.add(o);
            return true;
        }
        else throw new NullPointerException();
    }
    public Observer detach(Observer o){
        System.out.println("Detaching " + o.name);
        if(o==null) throw new NullPointerException();
        int index = list.indexOf(o);
        Observer O = list.remove(index);
        return O;
    }
    public void Notify(){
        if(list.size()==0)
            System.out.println("List is EMPTY");
        for(Observer o : list){
            if(o!=null){
                System.out.println("Updating observer "+ o.name);
                o.update(x,y); 
            }
            else throw new NullPointerException("In Notify");
        }
    }
    
    public void setState(int x, int y){
        this.x = x;
        this.y = y;
        Notify();
    }
}
