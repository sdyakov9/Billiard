import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window extends JFrame implements ActionListener {
    public Window() {
        setTitle("Billiard");
        setResizable(false);
        pack();

        setSize(900,
                600);

        Billiard content = new Billiard();
        setContentPane(content);

        Overlay overlay = new Overlay();
        setGlassPane(overlay);
        getGlassPane().setVisible(true);

        Timer timer = new Timer(20, this);
        timer.start();
    }

    // New frame
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
