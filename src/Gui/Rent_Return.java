package Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Rent_Return {
    private Rent rent;
    private Return returnGui;

    private JFrame frame;
    private JButton Rent;
    private JPanel Main_Frame;
    private JButton Return;
    private JButton backButton;

    public Rent_Return() {
        frame = new JFrame("Rent/Return");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setBounds(650,300,300,200); //370,300
        frame.setResizable(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Run.menu.visible();
                frame.dispose();
            }
        });
        Rent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rent == null){
                    rent = new Rent();
                }else{
                    rent.userFilter();
                    rent.mediaFilter();
                    rent.visible();
                }
                frame.dispose();
            }
        });
        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(returnGui == null){
                    returnGui = new Return();
                }else{
                    returnGui.filter();
                    returnGui.visible();
                }
                frame.dispose();
            }
        });
    }


    public Gui.Rent getRent() {
        return rent;
    }

    public void visible(){
        frame.setVisible(true);
    }
}
