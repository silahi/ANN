package ai.alhous;

import javax.swing.JFrame;

public class Charfane extends JFrame {
    public Charfane(){
        setSize(1000,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    }

    


    public static void main(String[] args) {
        Charfane charfane = new Charfane();
        charfane.setVisible(true);
    }
}
