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
public abstract class Observer {
    protected String name;
    public abstract void update(int x, int y);
}
