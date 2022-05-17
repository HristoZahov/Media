package Gui;

import javax.swing.*;

public class Rent_Return {
    private JButton Rent;
    private JPanel Main_Frame;
    private JButton Return;

    public Rent_Return() {
        JFrame frame = new JFrame("Rent");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setBounds(650,300,300,300); //370,300
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        new Rent_Return();
    }
}
