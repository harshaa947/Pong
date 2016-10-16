/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4.pong;

import javax.swing.JFrame;
import javax.swing.JPanel;
import pkg4.pong.game.GamePanel;
import pkg4.pong.StartInput;
import pkg4.pong.network.Data;
/**
 *
 * @author Vikas
 */

public class Pong extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StartInput.createAndShowGUI();
            }
        });
        
    }
    
    public Pong(String s) {
        
        setSize(GamePanel.WINDOW_WIDTH_Margin, GamePanel.WINDOW_HEIGHT_Margin);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel game = new GamePanel(4,3);
        
        Data.getInstance().Game = game;
        add(game);
        setVisible(true);
        
    }
    public void setInvis(){
        this.setVisible(false);
    }
    /*
    public void getText(){
        setSize(GamePanel.WINDOW_WIDTH_Margin, GamePanel.WINDOW_HEIGHT_Margin);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add (new StartInput());
        setVisible(true);
        
    }*/
    
    
}
