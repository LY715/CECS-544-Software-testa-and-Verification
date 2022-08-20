/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statepattern;

import java.awt.GridLayout;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Daddy
 */
public class StatePattern {

    /**
     * @param args the command line arguments
     */
    private static final int FRAME_WIDTH = 380;
    private static final int FRAME_HEIGHT = 100;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        JFrame frame = new JFrame("State Pattern Soda Machine ");
        JButton coinIn = new JButton("Deposit $0.25");
        JButton returnCoin = new JButton("Return Coins");
        JButton sodaDrop = new JButton("Mountain Dew");
        final JLabel label = new JLabel("$0.00");
     
        
        JPanel panel = new JPanel();
       // panel.setLayout(new GridLayout(3,2));
        panel.add(coinIn);
        panel.add(returnCoin);
        panel.add(sodaDrop);
        panel.add(label);
     //   panel.setBounds(0,40,FRAME_WIDTH, FRAME_WIDTH);
       
        frame.add(panel);
        
        final SodaMachine s = new SodaMachine();
       
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        class CoinInListener implements ActionListener {
            public void actionPerformed(ActionEvent event){
                label.setText(s.CoinSlotDeposit());
            }
        }
        class CoinReturnListener implements ActionListener {
            public void actionPerformed(ActionEvent event){
                label.setText(s.CoinReturn());
            }
        }
        class SodaDropListener implements ActionListener {
            public void actionPerformed(ActionEvent event){
                label.setText(s.SodaButton());
            }
        }
        ActionListener coinDropButton = new CoinInListener();
        coinIn.addActionListener(coinDropButton);
        
        ActionListener coinReturnButton = new CoinReturnListener();
        returnCoin.addActionListener(coinReturnButton);
        
        ActionListener sodaDropButton = new SodaDropListener();
        sodaDrop.addActionListener(sodaDropButton);
        
        System.out.println("Soda Machine\n0. Coin Return\n1.Deposit Coin\n2.Soda Button");
     /*  while(true){
            int c = System.in.read();
       
            if(c=='0') s.CoinReturn();
            if(c=='1') 
            if(c=='2') s.SodaButton();
       } */
    }
    
}
