import view.StartView;

import javax.swing.*;
import java.awt.*;

public class Kiosk extends JFrame {
    private JPanel mainPanel;

    public Kiosk(){
        mainPanel = new StartView();
        initialise();
    }

    public void initialise(){
        GridLayout layout = new GridLayout();
        setLayout(layout);
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        pack();
        mainPanel.setVisible(true);
    }

    public static void main(String[] args){
        Kiosk window = new Kiosk();
        window.setVisible(true);
    }
}
