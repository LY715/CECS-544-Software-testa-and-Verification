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
public class GOF_Observer_Structure {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Subject s = new Subject();
        
        Observer1 o1 =  new Observer1("Dr. Gumbo");
        s.attach(o1);
        
        s.setState(2,3);
        System.out.println("================================");
        Observer2 o2 = new Observer2("Mr. Muffuletta");
        s.attach(o2);
        
        s.setState(3,4);
        System.out.println("================================"); 
        o2 = new Observer2("Rev. Etouffe");
        s.attach(o2);
        s.setState(4,5);
        System.out.println("================================"); 
        s.detach(o2);
        s.setState(0, 0);
    }
    
}
